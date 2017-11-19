package ltd.scau.c0mmuni0n.dao.mybatis.mapper;

import java.util.List;
import ltd.scau.c0mmuni0n.dao.mybatis.po.Authorities;
import ltd.scau.c0mmuni0n.dao.mybatis.po.AuthoritiesExample;
import org.apache.ibatis.annotations.Param;

public interface AuthoritiesMapper {
    long countByExample(AuthoritiesExample example);

    int deleteByExample(AuthoritiesExample example);

    int insert(Authorities record);

    int insertSelective(Authorities record);

    List<Authorities> selectByExample(AuthoritiesExample example);

    int updateByExampleSelective(@Param("record") Authorities record, @Param("example") AuthoritiesExample example);

    int updateByExample(@Param("record") Authorities record, @Param("example") AuthoritiesExample example);
}