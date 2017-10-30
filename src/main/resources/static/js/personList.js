/**
 * Created by Guo Zhaotong on 2017/10/13.
 */


var vm = new Vue({
    el: '#app',
    data: {
        items: {},
        updateName: "",
        updateGrade: "",
        addName: "",
        addGrade: "",
        curPerson: {},
    },
    methods: {
        getAllPeople: function () {
            $.ajax({
                type: "get",
                url: appname + "/person/list",
                data: {},
                success: function (data) {
                    vm.items = data
                }
            });
        },
        editPerson: function () {
            if (vm.addName === "") {
                alert("请输入姓名！");
                return
            }
            else if (vm.addGrade === "") {
                alert("请选择年级！");
                return
            }
            else {
                $.ajax({
                    type: "post",
                    url: appname + "/person/add",
                    data: {name: vm.addName, grade: vm.addGrade},
                    success: function (data) {
                        vm.getAllPeople();
                        $("#myModal").modal('toggle');
                    }
                });
            }
        },

        updatePerson: function () {
            if (vm.updateName === "") {
                alert("请输入姓名！");
                return
            }
            else if (vm.updateGrade === "") {
                alert("请选择年级！");
                return
            }
            else {
                $.ajax({
                    type: "post",
                    url: appname + "/person/update",
                    data: {id: vm.curPerson.id, name: vm.updateName, grade: vm.updateGrade},
                    success: function (data) {
                        vm.getAllPeople();
                        $("#myEditModal").modal('toggle');
                    }
                });
            }
        },
        transPerson: function (person) {
            vm.curPerson = person;
            $("#myEditModal").modal('toggle');
            vm.updateName = vm.curPerson.name;
            vm.updateGrade = vm.curPerson.grade;
        },
        showAddPerson: function () {
            $("#myModal").modal('toggle');
        },
        deletePerson: function () {
            $.ajax({
                type: "post",
                url: appname + "/person/delete",
                data: {id: vm.curPerson.id},
                success: function (data) {
                    vm.getAllPeople();
                    $("#myEditModal").modal('toggle');
                }
            });
        },
        ifshow: function (name, obj) {
            if (JSON.stringify(obj).indexOf(name) !== -1) {
                return true
            }
            return false
        },
    }
})

vm.getAllPeople()


