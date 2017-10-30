/**
 * Created by Guo Zhaotong on 2017/10/10.
 */

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};

var isCheckAll = false;
var vm = new Vue({
    el: '#app',
    data: {
        items: {},
        recordId: "",
        id: "",
        recoderid: "",
        record_text: "",
        nonAttend_items: new Array(),
        nonAttend_one: null,
        sucAttachments: new Array(),
        fileName: "",
        newOrUpdate: "",
        date: "",
        time: "",
        place: "",
        recorder: "",
        content: "",
        verification: "",
        attPersons: {},
    },
    methods: {
        newOrEdit: function () {
            if (getQueryString("id") === null) {
                vm.newOrUpdate = "new";
                //执行一个laydate实例
                laydate.render({
                    elem: '#date',
                    value: new Date()
                });
            }
            else {
                vm.newOrUpdate = "update";
                vm.recordId = getQueryString("id");
                vm.getRecord();
            }
        },
        getRecord: function () {
            $.ajax({
                type: "get",
                url: appname + "/record/showOne",
                data: {id: vm.recordId},
                success: function (data) {
                    vm.date = data.date
                    $('#date').val(vm.date)
                    //执行一个laydate实例
                    laydate.render({
                        elem: '#date',
                        value: new Date(vm.date)
                    });
                    vm.time = data.time
                    $('#time').val(vm.time)
                    vm.place = data.place
                    $('#place').val(vm.place)
                    vm.recorder = data.recorder
                    $('#recorder').val(vm.recorder)
                    vm.verification = data.verification
                    vm.sucAttachments = data.attachment
                    vm.attPersons = data.attPersons
                    for (index in vm.attPersons) {
                        $("input[name='" + vm.attPersons[index].name + "']").attr("checked", 'true');
                    }
                    vm.nonAttend_items = data.nonAttPersons
                    $('#edit > div.froala-element').html(data.content);
                }
            });
        },
        autoComplete: function (subjects) {
            $('#recorder').typeahead({
                // source: subjects,
                source: function (query, process) {
                    process(subjects);
                },
                displayText: function (item) {//要匹配的东西，根据这个来搜索
                    console.log(item.name)
                    return item.name
                },
                updater: function (item) {  //显示的东西
                    vm.recoderid = item.id;
                    return item.name; //这里一定要return，否则选中不显示
                },
                items: 8, //显示8条
                delay: 50 //延迟时间
            });
        }
        ,
        autoComplete2: function (subjects) {
            $('#nonAttender').typeahead({
                // source: subjects,
                source: function (query, process) {
                    process(subjects);
                },
                displayText: function (item) {//要匹配的东西，根据这个来搜索
                    return item.name
                },
                updater: function (item) {  //显示的东西
                    vm.nonAttend_one = item;
                    return item.name; //这里一定要return，否则选中不显示
                },
                items: 8, //显示8条
                delay: 50 //延迟时间
            });
        }
        ,
        getAllPeople: function () {
            $.ajax({
                type: "get",
                url: appname + "/person/list",
                data: {},
                success: function (data) {
                    vm.items = data
                    vm.autoComplete(data)
                    vm.autoComplete2(data)
                }
            });
        },
        swapCheck: function () {
            if (isCheckAll) {
                $("input[type='checkbox']").each(function () {
                    this.checked = false;
                });
                isCheckAll = false;
            } else {
                $("input[type='checkbox']").each(function () {
                    this.checked = true;
                });
                isCheckAll = true;
            }
        },
        appendNonAttender: function () {
            if (vm.nonAttend_one == null) {
                alert('请先在输入框中选择已经存在的人名' + "\n\n （提示：先输入姓氏，然后点击提示框中的人名）")
                return
            }
            if ($('#nonAttender').val() == "" && $('#nonAttend_reason').val() == "") {
                alert("没有内容不能提交！")
                return
            }
            else if ($('#nonAttender').val() == "") {
                alert("请输入请假人！")
                return
            }
            else if ($('#nonAttend_reason').val() == "") {
                alert("不能无缘无故请假！")
                return
            }
            else {
                vm.nonAttend_one['personName'] = $('#nonAttender').val()
                vm.nonAttend_one['reason'] = $('#nonAttend_reason').val()
                vm.nonAttend_items.push(vm.nonAttend_one)
                vm.nonAttend_one = null
                $('#nonAttender').val("")
                $('#nonAttend_reason').val("")
            }
        },
        deleteNonAttender: function (it) {
            vm.nonAttend_items.splice($.inArray(it, vm.nonAttend_items), 1)
        },
        saveT: function () {
            if (vm.newOrUpdate == "new") {
                if ($('#recorder').val() == "") {
                    alert("请输入主持记录人！\n\n（提示：先输入姓氏，然后点击提示框中的人名）")
                }
                else if ($('#edit > div.froala-element.not-msie.f-basic').html().length < 20) {
                    alert("会议记录太短了吧！")
                }
                else {
                    var attend_persons = new Array();
                    for (index in vm.items) {
                        if ($("input[name=" + vm.items[index].name + "]").is(':checked')) {
                            attend_persons.push(vm.items[index])
                        }
                    }
                    ;
                    dateVar = $('#date').val()
                    $.ajax({
                        type: "post",
                        url: appname + "/record/add",
                        // contentType: "application/x-www-form-urlencoded",
                        data: {
                            date: dateVar,
                            time: $('#time').val(),
                            place: $('#place').val(),
                            recorder: $('#recorder').val(),
                            // content: $('#edit').froalaEditor('html.get', true),
                            content: $('#edit > div.froala-element').html(),

                            verification: '0',
                            attachment: JSON.stringify(vm.sucAttachments),
                            attList: JSON.stringify(attend_persons),
                            nonAttList: JSON.stringify(vm.nonAttend_items),
                        },
                        success: function (data) {
                            alert("暂存成功！")
                        }
                    });
                }
            }
            else {
                if ($('#recorder').val() == "") {
                    alert("请输入主持记录人！\n\n（提示：先输入姓氏，然后点击提示框中的人名）")
                }
                else if ($('#edit > div.froala-element.not-msie.f-basic').html().length < 20) {
                    alert("会议记录太短了吧！")
                }
                else {
                    var attend_persons = new Array();
                    for (index in vm.items) {
                        if ($("input[name=" + vm.items[index].name + "]").is(':checked')) {
                            attend_persons.push(vm.items[index])
                        }
                    }
                    ;
                    dateVar = $('#date').val()
                    $.ajax({
                        type: "post",
                        url: appname + "/record/update",
                        data: {
                            id: getQueryString("id"),
                            date: dateVar,
                            time: $('#time').val(),
                            place: $('#place').val(),
                            recorder: $('#recorder').val(),
                            // content: $('#edit').froalaEditor('html.get', true),
                            content: $('#edit > div.froala-element').html(),
                            verification: '0',
                            attachment: JSON.stringify(vm.sucAttachments),
                            attList: JSON.stringify(attend_persons),
                            nonAttList: JSON.stringify(vm.nonAttend_items),
                        },
                        success: function (data) {
                            alert("暂存成功！")
                            window.location.href = "showMeetingRecord.html?id=" + getQueryString("id");
                        }
                    });
                }
            }
        },
        deleteAtt: function (it) {
            console.log(it)
            $.ajax({
                type: "post",
                url: appname + "/record/deleteAttach",
                data: {
                    fileName: it.timeStamp + it.fileName,
                },
                success: function (data) {
                    if (data.code == 200) {
                        alert("删除成功！");
                        vm.sucAttachments.splice($.inArray(it, vm.sucAttachments), 1)
                    }
                    else {
                        alert("删除失败")
                    }
                }
            });
        },
        init: function () {
            $(function () {
                $('#edit').editable(
                    {
                        inlineMode: false,
                        theme: 'royal',
                        height: '400px', //高度,
                        language: "zh_cn",
                    }
                )
            });
            $(function () {
                $("#btnsubmit").click(function () {
                    if ($("input[name='attachment']").val() == "") {
                        alert("no Att")
                        return
                    }
                    $("#form1").ajaxSubmit({
                        success: function (data) {
                            vm.sucAttachments.push(data)
                        },
                        error: function (error) {
                            console.log(error)
                        },
                        url: '/record/addAttach', /*设置post提交到的页面*/
                        type: "post", /*设置表单以post方法提交*/
                        dataType: "json" /*设置返回值类型为文本*/
                    });
                });
            });
        }
    }
})
vm.init()
vm.newOrEdit()

vm.getAllPeople()