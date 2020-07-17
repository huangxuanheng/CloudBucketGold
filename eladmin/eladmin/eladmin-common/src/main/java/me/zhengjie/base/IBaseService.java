package me.zhengjie.base;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * author harry
 * dept: cloudpivot
 * date : 2020/3/30
 * des:
 */
public interface IBaseService<T extends BaseEntity> {
    /**
     * 存储记录
     * @param t
     */
    default void save(T t){
        BaseRepository jpaRepository = getJpaRepository();
        jpaRepository.save(t);
    }

    /**
     * 更新一条记录
     * @param t
     */
    default void update(T t){
        JpaRepository jpaRepository = getJpaRepository();
        jpaRepository.saveAndFlush(t);
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
    default T get(long id){
        JpaRepository<T,Long> jpaRepository = getJpaRepository();
        T s = jpaRepository.findById(id).get();
        return s;
    }

    /**
     * 获取所有对象
     * @return
     */
    default List<T> getAll(){
        JpaRepository<T,Long> jpaRepository = getJpaRepository();
        return jpaRepository.findAll();
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
    default Page<T> getAllByPage(Specification<T> specification, Pageable pageable){
        if(specification==null){
            throw new RuntimeException("匹配规则为空");
        }
        BaseRepository<T,Long> jpaRepository = getJpaRepository();
        Page<T> all = jpaRepository.findAll(specification, pageable);
        return all;
    }

    /**
     * 获取操作数据库对象
     * @return
     */
    BaseRepository getJpaRepository();
}
