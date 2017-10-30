/**
 * Created by shilei on 2017/7/19.
 */
var appname = ""
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
$(document).ready(function () {


    var vm = new Vue({
        el: '#app',
        data: {
            recordId: "",
            items: {},
            date: "",
            time: "",
            place: "",
            recorder: "",
            content: "",
            verification: "",
            attachment: {},
            attPersons: {},
            nonAttPersons: {},
            nonAttCou: {},
        },
        methods: {
            showRecord: function () {
                $.ajax({
                    type: "get",
                    url: appname + "/record/showOne",
                    data: {id: getQueryString("id")},
                    success: function (data) {
                        vm.date = data.date
                        vm.time = data.time
                        vm.place = data.place
                        vm.recorder = data.recorder
                        vm.content = data.content
                        vm.verification = data.verification
                        vm.attachment = data.attachment
                        vm.attPersons = data.attPersons
                        vm.nonAttPersons = data.nonAttPersons
                    }
                });
            },
            ifshow: function (name, obj) {
                if (JSON.stringify(obj).indexOf(name) !== -1) {
                    return true
                }
                return false
            },
            ifExist: function (obj) {
                if (obj.toString() <= 4) {
                    return false
                }
                return true
            },
            stasticNon: function () {
                $.ajax({
                    type: "get",
                    url: appname + "/nonAttendance/listNon",
                    data: {id: getQueryString("id")},
                    success: function (data) {
                        console.log(data)
                        vm.nonAttCou = data
                        // 指定图表的配置项和数据
                        var option = {
                            title: {
                                // text: '请假次数统计：'
                            },
                            tooltip: {},
                            legend: {
                                data: ['请假次数']
                            },
                            xAxis: {
                                data: vm.nonAttCou.all.map(function (m) {
                                    return m.name
                                })
                            },
                            yAxis: {},
                            series: [{
                                name: '请假次数',
                                type: 'bar',
                                data: vm.nonAttCou.all.map(function (m) {
                                    return m.number
                                })
                            }]
                        };
                        myChart.setOption(option);

                    }
                });
            }
        }
    })

    vm.recordId = getQueryString("id")
    vm.showRecord()
    vm.stasticNon()
    var myChart = echarts.init(document.getElementById('main'));

    // myChart.setOption(option);

})


