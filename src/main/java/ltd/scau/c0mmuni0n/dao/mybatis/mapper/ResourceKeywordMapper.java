package ltd.scau.c0mmuni0n.dao.mybatis.mapper;

import java.util.List;
import ltd.scau.c0mmuni0n.dao.mybatis.po.ResourceKeyword;
import ltd.scau.c0mmuni0n.dao.mybatis.po.ResourceKeywordExample;
import org.apache.ibatis.annotations.Param;

public interface ResourceKeywordMapper {
    long countByExample(ResourceKeywordExample example);

    int deleteByExample(ResourceKeywordExample example);

    int insert(ResourceKeyword record);

    int insertSelective(ResourceKeyword record);

    List<ResourceKeyword> selectByExample(ResourceKeywordExample example);

    int updateByExampleSelective(@Param("record") ResourceKeyword record, @Param("example") ResourceKeywordExample example);

    int updateByExample(@Param("record") ResourceKeyword record, @Param("example") ResourceKeywordExample example);
}