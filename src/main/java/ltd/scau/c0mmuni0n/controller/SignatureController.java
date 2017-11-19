package ltd.scau.c0mmuni0n.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/oss")
public class SignatureController {

    private static final Log log = LogFactory.getLog(SignatureController.class);

    @Autowired
    private HttpServletRequest request;

    @Value("#{properties.accessId}")
    private String accessId;

    @Value("#{properties.accessKey}")
    private String accessKey;

    @Value("#{properties.bucket}")
    private String bucket;

    @Value("#{properties.endpoint}")
    private String endPoint;

    @Autowired
    private OSSClient ossClient;

    @RequestMapping("/signature")
    @ResponseBody
    public String signature(HttpServletResponse response) throws UnsupportedEncodingException {

        String dir = "test/";

        String host = String.format("https://%s.%s", bucket, endPoint);

        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 100 * 1024 * 1024);
        policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
        byte[] binarData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binarData);
        String postSignature = ossClient.calculatePostSignature(postPolicy);

        Map<String, String> result = new LinkedHashMap<>();
        result.put("accessid", accessId);
        result.put("policy", encodedPolicy);
        result.put("signature", postSignature);
        result.put("dir", dir);
        result.put("host", host);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");

        Gson gson = new Gson();
        return gson.toJson(result);
    }
}
