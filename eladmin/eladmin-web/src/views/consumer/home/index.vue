<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.name" clearable size="small" placeholder="输入名称搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-date-picker
          v-model="query.createTime"
          :default-time="['00:00:00','23:59:59']"
          type="daterange"
          range-separator=":"
          size="small"
          class="date-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
    </div>
    <!--表单组件-->
    <el-dialog append-to-body :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
      <el-form ref="form" inline :model="form" size="small" label-width="80px">

        <el-form-item label="用户" prop="users">
          <el-select
            v-model="form.userId"
            style="width: 178px"
            placeholder="请选择"
            @change="changeUser"
          >
            <el-option
              v-for="item in users"
              :key="item.username"
              :label="item.username"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="级别" prop="grades">
          <el-select
            v-model="form.gradeId"
            style="width: 178px"
            placeholder="请选择"
            @change="changeGrade"
          >
            <el-option
              v-for="item in grades"
              :key="item.name"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="上级" prop="parents">
          <el-select
            v-model="form.parentId"
            style="width: 178px"
            placeholder="请选择"
            @change="changeParent"
          >
            <el-option
              v-for="item in parents"
              :key="item.user.username"
              :label="item.user.username"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" style="width: 370px;" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>
    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      lazy
      :load="getConsumerDatas"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
      :data="crud.data"
      row-key="id"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="用户" prop="user.username" />
      <el-table-column label="级别" prop="grade.name" />
      <el-table-column label="上级" prop="parent.user.username" />
      <el-table-column label="备注" align="center" prop="remark" />

      <el-table-column v-permission="['admin','dept:edit','dept:del']" label="操作" width="130px" align="center" fixed="right">
        <template slot-scope="scope">
          <udOperation
            :data="scope.row"
            :permission="permission"
            msg="确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！"
          />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import crudConsumer from '@/api/consumer/consumer'
import crudUser from '@/api/system/user'
import crudGrade from '@/api/consumer/grade'

import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'

let consumer_users=[]
let consumer_parents=[]
let consumer_grades=[]
const defaultForm = { id: null, parentId: null, remark: '', userId: null, gradeId: null, parent: null, grade: null, user: null, users: [], parents: [], grades: [] }
export default {
  name: 'Consumer',
  components: { Treeselect, crudOperation, rrOperation, udOperation },
  cruds() {
    return CRUD({ title: '消费商级别', url: 'api/consumer/list', crudMethod: { ...crudConsumer }})
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  // 设置数据字典
  dicts: ['dept_status'],

  data() {
    return {
      parents: [],
      users: [],
      permission: {
        add: ['admin', 'consumer:add'],
        edit: ['admin', 'consumer:edit'],
        del: ['admin', 'consumer:del']
      },
      grades: []
    }
  },
  methods: {
    getConsumerDatas(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        crudConsumer.getConsumers(params).then(res => {
          resolve(res.content)
        })
      }, 100)
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      if (form.parentId != 0) {
        this.getParent(form.parentId)
      } else {
        this.getConsumers()
      }
      if (!form.id) {
        this.getUsers()
        this.getGrades()
        this.getParents()
      }
    },
    // 打开编辑弹窗前做的操作
    [CRUD.HOOK.beforeToEdit](crud, form) {
      this.getUsers()
      this.getGrades()
      this.getParents()
      // form.parents.push(form.parent)
      // form.users.push(form.user)
      // form.grades.push(form.grade)
      consumer_users=[]
       const users = []
      form.users.forEach(function(job, index) {
        users.push(job.id)
        // 初始化编辑时候的岗位
        const data = { id: job.id }
        consumer_users.push(data)
      })

      consumer_parents=[]
      const parents = []
      form.parents.forEach(function(job, index) {
        parents.push(job.id)
        // 初始化编辑时候的岗位
        const data = { id: job.id }
        consumer_parents.push(data)
      })
      consumer_grades=[]
      const grades = []
      form.parents.forEach(function(job, index) {
        grades.push(job.id)
        // 初始化编辑时候的岗位
        const data = { id: job.id }
        consumer_grades.push(data)
      })
      
       form.users=users
       form.parents=parents
       form.grades=grades
    },
    // 提交前做的操作
    [CRUD.HOOK.afterValidateCU](crud) {
      if (crud.form.userId ===0) {
        this.$message({
          message: '用户不能为空',
          type: 'warning'
        })
        return false
      } else if (crud.form.gradeId ===0) {
        this.$message({
          message: '级别不能为空',
          type: 'warning'
        })
        return false
      }
      
      // if (crud.form.parents.length === 0){
      //   crud.form.parentId=0
      // }else{
      //   crud.form.parentId = consumer_parents[0].id
      // }
      // crud.form.userId = consumer_users[0].id
      // crud.form.gradeId = consumer_grades[0].id
      
      return true
    },

    getParent(id) {
      crudConsumer.getConsumer(id).then(res => {
        this.parent = res.content
      })
    },
    getUsers() {
      crudUser.getUsers().then(res => {
        this.users = res.content
      })
    },
    getGrades() {
      crudGrade.getGrades().then(res => {
        this.grades = res.content
      })
    },

    getParents() {
      crudConsumer.getConsumers({ enabled: true }).then(res => {
        this.parents = res.content
      })
    },
    getConsumers() {
      crudConsumer.getConsumers({ enabled: true }).then(res => {
        this.parents = res.content
      })
    },
    changeUser(value) {
      consumer_users = [{id:value}]
      // value.forEach(function(data, index) {
      //   const job = { id: data }
      //   consumer_users.push(job)
      // })

    },

    changeParent(value) {
      consumer_parents = [{id:value}]
      // value.forEach(function(data, index) {
      //   const job = { id: data }
      //   consumer_parents.push(job)
      // })
    },

    changeGrade(value) {
      consumer_grades = [{id:value}]
      // value.forEach(function(data, index) {
      //   const job = { id: data }
      //   grades.push(job)
      // })
    },
    // 获取弹窗内部门数据
    loadConsumers({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        crudConsumer.getConsumers({ enabled: true, pid: parentNode.id }).then(res => {
          parentNode.children = res.content.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null
            }
            return obj
          })
          setTimeout(() => {
            callback()
          }, 100)
        })
      }
    },

    checkboxT(row, rowIndex) {
      return row.id !== 1
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  /deep/ .vue-treeselect__control, /deep/ .vue-treeselect__placeholder, /deep/ .vue-treeselect__single-value {
    height: 30px;
    line-height: 30px;
  }
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
  /deep/ .el-input-number .el-input__inner {
    text-align: left;
  }
</style>
