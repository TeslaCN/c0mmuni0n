package ltd.scau.c0mmuni0n.dao.mybatis.mapper;

import java.util.List;
import ltd.scau.c0mmuni0n.dao.mybatis.po.Type;
import ltd.scau.c0mmuni0n.dao.mybatis.po.TypeExample;
import org.apache.ibatis.annotations.Param;

public interface TypeMapper {
    long countByExample(TypeExample example);

    int deleteByExample(TypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Type record);

    int insertSelective(Type record);

    List<Type> selectByExample(TypeExample example);

    Type selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByExample(@Param("record") Type record, @Param("example") TypeExample example);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
}