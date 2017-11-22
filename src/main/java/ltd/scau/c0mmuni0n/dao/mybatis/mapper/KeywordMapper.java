package ltd.scau.c0mmuni0n.dao.mybatis.mapper;

import java.util.List;
import ltd.scau.c0mmuni0n.dao.mybatis.po.Keyword;
import ltd.scau.c0mmuni0n.dao.mybatis.po.KeywordExample;
import org.apache.ibatis.annotations.Param;

public interface KeywordMapper {
    long countByExample(KeywordExample example);

    int deleteByExample(KeywordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Keyword record);

    int insertSelective(Keyword record);

    List<Keyword> selectByExample(KeywordExample example);

    Keyword selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByExample(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByPrimaryKeySelective(Keyword record);

    int updateByPrimaryKey(Keyword record);
}