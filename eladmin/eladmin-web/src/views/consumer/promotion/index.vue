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

        <el-form-item label="促销名称" prop="name">
          <el-input v-model="form.name" style="width: 370px;" />
        </el-form-item>

        <el-form-item label="促销简介" prop="introduct">
          <el-input v-model="form.introduct" style="width: 370px;" />
        </el-form-item>

        <el-form-item label="促销规则" prop="rule">
          <el-input v-model="form.rule" style="width: 370px;" />
        </el-form-item>

        <el-form-item label="满" prop="condition">
          <el-input v-model="form.condition" style="width: 370px;" />
        </el-form-item>

        <el-form-item label="减" prop="condition">
          <el-input v-model="form.condition" style="width: 370px;" />
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
      <el-table-column :selectable="checkboxT" type="selection" width="55" />
      <el-table-column label="促销名称" prop="name" />
      <el-table-column label="促销简介" prop="introduct" />
      <el-table-column label="促销规则" prop="rule" />
      <el-table-column label="条件" prop="condition" />

      <el-table-column v-permission="['admin','dept:edit','dept:del']" label="操作" width="130px" align="center" fixed="right">
        <template slot-scope="scope">
          <udOperation
            :data="scope.row"
            :permission="permission"
            :disabled-dle="scope.row.id === 1"
            msg="确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！"
          />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import crudPromotion from '@/api/consumer/promotion'

import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'

const defaultForm = { id: null, name: null, introduct: null, rule: 0, condition: 0, reduce: 0 }
export default {
  name: 'Consumer',
  components: { Treeselect, crudOperation, rrOperation, udOperation },
  cruds() {
    return CRUD({ title: '促销', url: 'api/promotion/list', crudMethod: { ...crudPromotion }})
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
        crudPromotion.getConsumers(params).then(res => {
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
    },
    // 提交前做的操作
    [CRUD.HOOK.afterValidateCU](crud) {
      if (crud.form.name === null) {
        this.$message({
          message: '消费商名称不能为空',
          type: 'warning'
        })
        return false
      } else if (crud.form.parents.length === 0) {
        this.$message({
          message: '上级不能为空',
          type: 'warning'
        })
        return false
      } else if (crud.form.users.length === 0) {
        this.$message({
          message: '用户不能为空',
          type: 'warning'
        })
        return false
      } else if (crud.form.grades.length === 0) {
        this.$message({
          message: '级别不能为空',
          type: 'warning'
        })
        return false
      }
      crud.form.parentId = parents[0].id
      crud.form.userId = users[0].id
      crud.form.gradeId = grades[0].id
      return true
    },

    getParent(id) {
      crudPromotion.getConsumer(id).then(res => {
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
      crudPromotion.getConsumers({ enabled: true }).then(res => {
        this.parents = res.content
      })
    },
    getConsumers() {
      crudPromotion.getConsumers({ enabled: true }).then(res => {
        this.parents = res.content
      })
    },
    changeUser(value) {
      users = []
      value.forEach(function(data, index) {
        const job = { id: data }
        users.push(job)
      })
    },
    deleteUserTag(value) {
      users.forEach(function(data, index) {
        if (data.id === value) {
          users.splice(index, value)
        }
      })
    },
    changeParent(value) {
      parents = []
      value.forEach(function(data, index) {
        const job = { id: data }
        parents.push(job)
      })
    },
    deleteParentTag(value) {
      parents.forEach(function(data, index) {
        if (data.id === value) {
          parents.splice(index, value)
        }
      })
    },
    changeGrade(value) {
      grades = []
      value.forEach(function(data, index) {
        const job = { id: data }
        grades.push(job)
      })
    },
    deleteGradeTag(value) {
      grades.forEach(function(data, index) {
        if (data.id === value) {
          grades.splice(index, value)
        }
      })
    },
    // 获取弹窗内部门数据
    loadConsumers({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        crudPromotion.getConsumers({ enabled: true, pid: parentNode.id }).then(res => {
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
