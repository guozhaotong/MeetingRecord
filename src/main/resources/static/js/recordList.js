/**
 * Created by Guo Zhaotong on 2017/10/13.
 */


var vm = new Vue({
    el: '#app',
    data: {
        records: {},
    },
    methods: {
        getAllRecordList: function () {
            $.ajax({
                type: "get",
                url: appname + "/record/list",
                data: {},
                success: function (data) {
                    vm.records = data
                }
            });
        },
        formatDate: function (nowString) {
            var now = new Date(nowString);
            var year = now.getYear() + 1900;
            var month = now.getMonth() + 1;
            var date = now.getDate();
            return year + "年" + month + "月" + date + "日";
        }
    }
})


vm.getAllRecordList()


