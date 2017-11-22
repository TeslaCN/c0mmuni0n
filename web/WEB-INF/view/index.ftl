<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Home Page</title>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <script src="https://cdn.bootcss.com/vue/2.5.3/vue.js"></script>
</head>
<body>
<div class="container">
    <div class="jumbotron">
        <h1>Hello, world!</h1>
        <p>...</p>
        <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
    </div>
    <div class="row">
        <div id="resourcesList">
            <div v-for="item in resources">
                {{item.id}}<br>
                <a :href="'${OSS_PREFIX}' + item.path + item.name" target="_blank">{{item.name}}</a><br>
                {{item.username}}<br>
                {{item.size}}<br>
                {{item.type}}<br>
                <hr>
            </div>
            <div>
                Page: <span>{{page}}</span>
                <button @click="page += 1">Next Page</button>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var vm = new Vue({
        el: '#resourcesList',
        data: {
            page: 0,
            limit: 10,
            resources: []
        },
        watch: {
            page: function () {
                $.get('json/resources', {'page': this.page, 'limit': this.limit}, function (data) {
                    vm.resources = data;
                }, 'json')
            }
        },
        methods: {
            encode: function (target) {
                return encodeURI(target)
            }
        }
    });
    vm.page++;
</script>
</html>