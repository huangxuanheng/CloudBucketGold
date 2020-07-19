package me.zhengjie.base;


import me.zhengjie.annotation.DataTransform;
import me.zhengjie.utils.ModelUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/3/30
 * des:
 */
public interface IBaseService {
    /**
     * 存储记录
     * @param dto
     */
    default <T extends BaseDTO>void save(T dto){
        BaseRepository jpaRepository = getJpaRepository();
        jpaRepository.save(ModelUtils.toEntity(dto,getTansformClazz("entity")));
    }

    default Class getTansformClazz(String type){
        DataTransform annotation = this.getClass().getAnnotation(DataTransform.class);
        if(annotation==null){
            return null;
        }
        if(type=="dto"){
            return annotation.dto();
        }else if(type=="entity"){
            return annotation.entity();
        }
        return null;
    }

    /**
     * 更新一条记录
     * @param dto
     */
    default <T extends BaseDTO>void update(T dto){
        JpaRepository jpaRepository = getJpaRepository();
        jpaRepository.saveAndFlush(ModelUtils.toEntity(dto,getTansformClazz("entity")));
    }

    /**
     * 删除一条记录
     * @param id
     */
    default void deleted(long id){
        JpaRepository jpaRepository = getJpaRepository();
        jpaRepository.deleteById(id);
    }

    /**
     * 获取一条记录
     * @param id
     * @return
     */
    default <T extends BaseDTO>T get(long id){
        JpaRepository<BaseEntity,Long> jpaRepository = getJpaRepository();
        BaseEntity s = jpaRepository.findById(id).orElse(null);
        if(s==null){
            return null;
        }
        return (T) ModelUtils.toModel(s,getTansformClazz("dto"));
    }

    /**
     * 获取所有对象
     * @return
     */
    default <T extends BaseDTO>List<T> getAll(){
        JpaRepository<BaseEntity,Long> jpaRepository = getJpaRepository();

        return ModelUtils.toModels(jpaRepository.findAll(),getTansformClazz("dto"));
    }

    /**
     * 根据指定的对象，和匹配规则，按照分页查询获取分页数据
     * @param specification 匹配规则，
     *                       案例：根据属性name，精确查询，忽略逐渐id的查询条件
     *                               Specification specification=new Specification<User>() {
     *             @Override
     *             public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
     *                 List<Predicate> predicates= Lists.newArrayList();
     *                 //模糊匹配name属性
     *                 predicates.add(criteriaBuilder.like(root.get("name"),user.getName()));
     *                 //模糊匹配nickName属性
     *                 predicates.add(criteriaBuilder.like(root.get("nickName"),user.getNickName()));
     *                 return  criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
     *             }
     *         };
     * @param pageable 分页查询对象，示例：Pageable pageable= PageRequest.of(0,10, Sort.Direction.ASC,"id");
     * @return
     */
/*    default Page<T> getAllByPage(Specification<T> specification, Pageable pageable){
        if(specification==null){
            throw new RuntimeException("匹配规则为空");
        }
        BaseRepository<T,Long> jpaRepository = getJpaRepository();
        Page<T> all = jpaRepository.findAll(specification, pageable);
        return all;
    }*/

    /**
     * 获取操作数据库对象
     * @return
     */
    BaseRepository getJpaRepository();
}
