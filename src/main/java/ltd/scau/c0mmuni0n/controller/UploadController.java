package ltd.scau.c0mmuni0n.controller;

import com.google.gson.Gson;
import ltd.scau.c0mmuni0n.dao.mybatis.mapper.*;
import ltd.scau.c0mmuni0n.dao.mybatis.po.Resource;
import ltd.scau.c0mmuni0n.dao.mybatis.po.Type;
import ltd.scau.c0mmuni0n.dao.mybatis.po.TypeExample;
import ltd.scau.c0mmuni0n.dao.mybatis.po.Users;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/upload")
public class UploadController {

    private static final Log log = LogFactory.getLog(UploadController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private ResourceKeywordMapper resourceKeywordMapper;

    @RequestMapping
    public ModelAndView upload() {
        ModelAndView view = new ModelAndView("upload");
        return view;
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(HttpServletResponse response, @RequestParam(value = "param", required = false) String param) {
        log.info("Uploaded empty");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        Gson gson = new Gson();
        Map<String, String> result = new HashMap<>();
        result.put("result", "empty");
        result.put("param", param);
        return gson.toJson(result);
    }

    @RequestMapping(value = "/success", method = RequestMethod.POST)
    @ResponseBody
    public String success(HttpServletResponse response, @RequestParam("name") String name, @RequestParam("size") Long size, @RequestParam("completeTimestamp") Long timestamp, @RequestParam("type") String type, @RequestParam("path") String path) {
        log.info(String.format("Upload Succeed: {name:'%s', type:'%s', size:%d, time:%s}", name, type, size, new Date(timestamp)));
        /*
       {"id":"o_1bva6v8as13ec1ek31rls1lfc12d38","name":"年级综测排名.xls","type":"application/vnd.ms-excel","relativePath":"","size":1300992,"origSize":1300992,"loaded":1300992,"percent":100,"status":5,"lastModifiedDate":"2017-09-24T15:24:35.000Z","completeTimestamp":1511097609560}
        */
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");

        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String username = securityContextImpl.getAuthentication().getName();

        Users user = usersMapper.selectByPrimaryKey(username);
        Resource resource = new Resource();
        resource.setName(name);
        resource.setSize(size);
        resource.setUploadtime(timestamp);
        resource.setUsername(user.getUsername());
        resource.setPath(path);

        Type typeObject = null;
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andTypeEqualTo(type);
        List<Type> types = typeMapper.selectByExample(typeExample);
        if (types.size() < 1) {
            typeObject = new Type();
            typeObject.setType(type);
            typeMapper.insertSelective(typeObject);
        } else if (types.size() == 1) {
            typeObject = types.get(0);
        }
        resource.setType(typeObject.getId());
        resourceMapper.insertSelective(resource);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        Gson gson = new Gson();
        return gson.toJson(result);
    }
}
