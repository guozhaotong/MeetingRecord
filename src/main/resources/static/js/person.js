/**
 * Created by Guo Zhaotong on 2017/10/9.
 */

$(document).ready(function () {


    var vm = new Vue({
        el: '#app',
        data: {
            selected: '',
            addName: ''
        },
        methods: {
            editPerson: function () {
                if (vm.addName === "") {
                    alert("请输入姓名！");
                    return
                }
                else if (vm.selected === "") {
                    alert("请选择年级！");
                    return
                }
                else {
                    $.ajax({
                        type: "post",
                        url: appname + "/person/add",
                        data: {name: vm.addName, grade: vm.selected},
                        success: function (data) {
                            console.log(data)
                        }
                    });
                }
            }
        }
    })
})


