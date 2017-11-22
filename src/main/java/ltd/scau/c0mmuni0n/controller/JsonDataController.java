package ltd.scau.c0mmuni0n.controller;

import com.google.gson.Gson;
import ltd.scau.c0mmuni0n.dao.mybatis.mapper.ResourceMapper;
import ltd.scau.c0mmuni0n.dao.mybatis.po.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/json")
public class JsonDataController {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private Gson gson;

    @RequestMapping(value = "/resources", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getResources(HttpServletResponse response, @RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        int offset = (page - 1) * limit;
        List<Resource> resources = resourceMapper.findByPage(offset, limit);
        return gson.toJson(resources);
    }

    @RequestMapping(value = "/resources/{id}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String getResourceDetail(HttpServletResponse response, @PathVariable Long id) {
        Resource resource = resourceMapper.selectByPrimaryKey(id);
        return resource != null ? gson.toJson(resource) : "{\"error\":\"Permission denied!\"}";
    }
}
