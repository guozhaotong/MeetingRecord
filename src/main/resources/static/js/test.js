$.ajax({
    type: "get",
    url: appname + "/person/list",
    data: {},
    success: function (data) {
        $('#recorder').typeahead({
            // source: subjects,
            source: function (query, process) {
                process(data);
            },
            displayText: function (item) {//要匹配的东西，根据这个来搜索
                console.log(item.name)
                return item.name
            },
            updater: function (item) {  //显示的东西
                return item.name; //这里一定要return，否则选中不显示
            },
            items: 8, //显示8条
            delay: 50 //延迟时间
        });

    }
});
