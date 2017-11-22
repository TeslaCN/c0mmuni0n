<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload</title>
    <script src="https://cdn.bootcss.com/plupload/2.3.6/plupload.full.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <style>
        .btn {
            color: #fff;
            background-color: #337ab7;
            border-color: #2e6da4;
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            text-decoration: none;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            background-image: none;
            border: 1px solid transparent;
            border-radius: 4px;
        }

        a.btn:hover {
            background-color: #3366b7;
        }

        .progress {
            margin-top: 2px;
            width: 200px;
            height: 14px;
            margin-bottom: 10px;
            overflow: hidden;
            background-color: #f5f5f5;
            border-radius: 4px;
            -webkit-box-shadow: inset 0 1px 2px rgba(0, 0, 0, .1);
            box-shadow: inset 0 1px 2px rgba(0, 0, 0, .1);
        }

        .progress-bar {
            background-color: rgb(92, 184, 92);
            background-image: linear-gradient(45deg, rgba(255, 255, 255, 0.14902) 25%, transparent 25%, transparent 50%, rgba(255, 255, 255, 0.14902) 50%, rgba(255, 255, 255, 0.14902) 75%, transparent 75%, transparent);
            background-size: 40px 40px;
            box-shadow: rgba(0, 0, 0, 0.14902) 0px -1px 0px 0px inset;
            box-sizing: border-box;
            color: rgb(255, 255, 255);
            display: block;
            float: left;
            font-size: 12px;
            height: 20px;
            line-height: 20px;
            text-align: center;
            transition-delay: 0s;
            transition-duration: 0.6s;
            transition-property: width;
            transition-timing-function: ease;
            width: 266.188px;
        }
    </style>
</head>
<body>

<h2>OSS web直传---在服务端java签名,浏览器直传</h2>
<ol>
    <li>基于plupload封装</li>
    <li>支持html5,flash,silverlight,html4 等协议上传</li>
    <li>可以运行在PC浏览器，手机浏览器，微信</li>
    <li>显示上传进度条</li>
</ol>
<br>
<form name=theform>
    <input type="radio" name="myradio" value="local_name" checked=true/> 上传文件名字保持本地文件名字
    <input type="radio" name="myradio" value="random_name"/> 上传文件名字是随机文件名, 后缀保留
</form>

<h4>您所选择的文件列表：</h4>
<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>

<br/>

<div id="container">
    <a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
    <a id="postfiles" href="javascript:void(0);" class='btn'>开始上传</a>
</div>

<pre id="console"></pre>
${(Session.SPRING_SECURITY_CONTEXT.authentication.principal.username)!}
<p>&nbsp;</p>
</body>
<#assign ctx=request.getContextPath()>
<script>

    accessid = ''
    accesskey = ''
    host = ''
    policyBase64 = ''
    signature = ''
    callbackbody = ''
    filename = ''
    key = ''
    expire = 0
    g_object_name = ''
    g_object_name_type = ''
    now = timestamp = Date.parse(new Date()) / 1000;

    function send_request() {
        var xmlhttp = null;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        if (xmlhttp != null) {
            serverUrl = '${ctx}/oss/signature'
            xmlhttp.open("GET", serverUrl, false);
            xmlhttp.send(null);
            return xmlhttp.responseText
        }
        else {
            alert("Your browser does not support XMLHTTP.");
        }
    };

    function post_result(file) {
        var xmlhttp = null
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP")
        }

        if (xmlhttp != null) {
            url = '${ctx}/upload/success'
            xmlhttp.open("POST", url, false)
            xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8;")
            xmlhttp.setRequestHeader('X-CSRF-TOKEN', '${_csrf.token}')
            params = '';
            for (i in file) {
                params += encodeURI(i) + '=' + encodeURI(file[i]) + '&'
            }
            params += 'path=' + encodeURI(get_uploaded_object_name(""))
            xmlhttp.send(params)
            return xmlhttp.responseText
        } else {
            alert("Your browser does not support XMLHTTP.");
        }
    }

    function check_object_radio() {
        var tt = document.getElementsByName('myradio');
        for (var i = 0; i < tt.length; i++) {
            if (tt[i].checked) {
                g_object_name_type = tt[i].value;
                break;
            }
        }
    }

    function get_signature() {
        //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
        now = timestamp = Date.parse(new Date()) / 1000;
        if (expire < now + 3) {
            body = send_request()
            var obj = eval("(" + body + ")");
            host = obj['host']
            policyBase64 = obj['policy']
            accessid = obj['accessid']
            signature = obj['signature']
            expire = parseInt(obj['expire'])
            callbackbody = obj['callback']
            key = obj['dir']
            return true;
        }
        return false;
    };

    function random_string(len) {
        len = len || 32;
        var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
        var maxPos = chars.length;
        var pwd = '';
        for (i = 0; i < len; i++) {
            pwd += chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return pwd;
    }

    function get_suffix(filename) {
        pos = filename.lastIndexOf('.')
        suffix = ''
        if (pos != -1) {
            suffix = filename.substring(pos)
        }
        return suffix;
    }

    function calculate_object_name(filename) {
        if (g_object_name_type == 'local_name') {
            g_object_name += "${r'${filename}'}"
        }
        else if (g_object_name_type == 'random_name') {
            suffix = get_suffix(filename)
            g_object_name = key + random_string(10) + suffix
        }
        return ''
    }

    function get_uploaded_object_name(filename) {
        if (g_object_name_type == 'local_name') {
            tmp_name = g_object_name
            tmp_name = tmp_name.replace("${r'${filename}'}", filename);
            return tmp_name
        }
        else if (g_object_name_type == 'random_name') {
            return g_object_name
        }
    }

    function set_upload_param(up, filename, ret) {
        if (ret == false) {
            ret = get_signature()
        }
        g_object_name = key;
        if (filename != '') {
            suffix = get_suffix(filename)
            calculate_object_name(filename)
        }
        new_multipart_params = {
            'key': g_object_name,
            'policy': policyBase64,
            'OSSAccessKeyId': accessid,
            'success_action_status': '200', //让服务端返回200,不然，默认会返回204
            'callback': callbackbody,
            'signature': signature,
        };

        up.setOption({
            'url': host,
            'multipart_params': new_multipart_params
        });

        up.start();
    }

    var uploader = new plupload.Uploader({
        runtimes: 'html5,flash,silverlight,html4',
        browse_button: 'selectfiles',
        //multi_selection: false,
        container: document.getElementById('container'),
        flash_swf_url: 'https://c0mmuni0n.oss-cn-shenzhen.aliyuncs.com/plupload-2.3.6/js/Moxie.swf',
        silverlight_xap_url: 'https://c0mmuni0n.oss-cn-shenzhen.aliyuncs.com/plupload-2.3.6/js/Moxie.xap',
        url: 'https://oss-cn-shenzhen.aliyuncs.com',

        filters: {
//            mime_types: [
//                {title: "Image files", extensions: "jpg,gif,png,bmp"},
//                {title: "Zip files", extensions: "zip,rar"}
//            ],
            max_file_size: '100mb', //最大只能上传100mb的文件
            prevent_duplicates: true //不允许选取重复文件
        },

        init: {
            PostInit: function () {
                document.getElementById('ossfile').innerHTML = '';
                document.getElementById('postfiles').onclick = function () {
                    set_upload_param(uploader, '', false);
                    return false;
                };
            },

            FilesAdded: function (up, files) {
                plupload.each(files, function (file) {
                    document.getElementById('ossfile').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ')<b></b>'
                            + '<div class="progress"><div class="progress-bar" style="width: 0%"></div></div>'
                            + '</div>';
                });
            },

            BeforeUpload: function (up, file) {
                check_object_radio();
                set_upload_param(up, file.name, true);
            },

            UploadProgress: function (up, file) {
                var d = document.getElementById(file.id);
                d.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
                var prog = d.getElementsByTagName('div')[0];
                var progBar = prog.getElementsByTagName('div')[0]
                progBar.style.width = 2 * file.percent + 'px';
                progBar.setAttribute('aria-valuenow', file.percent);
            },

            FileUploaded: function (up, file, info) {
                /*
                {"id":"o_1bva6v8as13ec1ek31rls1lfc12d38","name":"年级综测排名.xls","type":"application/vnd.ms-excel","relativePath":"","size":1300992,"origSize":1300992,"loaded":1300992,"percent":100,"status":5,"lastModifiedDate":"2017-09-24T15:24:35.000Z","completeTimestamp":1511097609560}
                 */
                if (info.status == 200) {
//                    console.log(JSON.stringify(file))
                    post_result(file)
                    document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = 'upload to oss success, object name:' + get_uploaded_object_name(file.name);
                }
                else {
                    document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = info.response;
                }
            },
            Error: function (up, err) {
                if (err.code == -600) {
                    document.getElementById('console').appendChild(document.createTextNode("\n选择的文件太大了"));
                }
                else if (err.code == -601) {
                    document.getElementById('console').appendChild(document.createTextNode("\n选择的文件后缀不对"));
                }
                else if (err.code == -602) {
                    document.getElementById('console').appendChild(document.createTextNode("\n这个文件已经上传过一遍了"));
                }
                else {
                    document.getElementById('console').appendChild(document.createTextNode("\nError xml:" + err.response));
                }
            }
        }
    });

    uploader.init();
</script>
</html>