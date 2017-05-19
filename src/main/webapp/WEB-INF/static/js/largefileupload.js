/**
 * 大文件上传
 */
var userInfo = {userId:"kazaff", md5:""};   //用户会话信息
var chunkSize = 5 * 1024 * 1024;        //分块大小5M
var uniqueFileName = null;          //文件唯一标识符
var md5Mark = null;

//function getServer(type){   //测试用，根据不同类型的后端返回对应的请求地址
//    switch(type){
//        case "php": return "./serverPHP/fileUpload.php"
//        case "node": return "http://localhost:3000/fileUpload";
//        case "java": return "${ctx}/fileUpload";
//        case "dubbo": return "http://127.0.0.1:8888/fileUpload";
//    }
//}
//
//在jsp页面中定义
//var backEndUrl = getServer("java");
//var checkUrl = "${ctx}/fileCheck";

WebUploader.Uploader.register({
    "before-send-file": "beforeSendFile"
    , "before-send": "beforeSend"
    , "after-send-file": "afterSendFile"
}, {
    beforeSendFile: function(file){
        //秒传验证
        var task = new $.Deferred();
        var start = new Date().getTime();
		//计算文件md5值，验证是否存在
        (new WebUploader.Uploader()).md5File(file, 0, 10*1024*1024).progress(function(percentage){
            console.log(percentage);
        }).then(function(val){
            console.log("总耗时: "+((new Date().getTime()) - start)/1000);
			console.log(val);
            md5Mark = val;
            userInfo.md5 = val;

            $.ajax({
                type: "POST"
                , url: checkUrl
                , data: {
                    status: "md5Check"
                    , md5: val
                }
                , cache: false
                , timeout: 1000 //todo 超时的话，只能认为该文件不曾上传过
                , dataType: "json"
            }).then(function(data, textStatus, jqXHR){

                //console.log(data);

                if(data.ifExist){   //若存在，这返回失败给WebUploader，表明该文件不需要上传
                    task.reject();

                    uploader.skipFile(file);
                    file.path = data.path;
                    UploadComlate(file);
                }else{
                    task.resolve();
                    //拿到上传文件的唯一名称，用于断点续传
                    uniqueFileName = md5(''+userInfo.userId+file.name+file.type+file.lastModifiedDate+file.size);
                }
            }, function(jqXHR, textStatus, errorThrown){    //任何形式的验证失败，都触发重新上传
                task.resolve();
                //拿到上传文件的唯一名称，用于断点续传
                uniqueFileName = md5(''+userInfo.userId+file.name+file.type+file.lastModifiedDate+file.size);
            });
        });
        return $.when(task);
    }
    , beforeSend: function(block){
        //分片验证是否已传过，用于断点续传
        var task = new $.Deferred();
        $.ajax({
            type: "POST"
            , url: checkUrl
            , data: {
                status: "chunkCheck"
                , name: uniqueFileName
                , chunkIndex: block.chunk
                , size: block.end - block.start
            }
            , cache: false
            , timeout: 1000 //todo 超时的话，只能认为该分片未上传过
            , dataType: "json"
        }).then(function(data, textStatus, jqXHR){
            if(data.ifExist){   //若存在，返回失败给WebUploader，表明该分块不需要上传
                task.reject();
            }else{
                task.resolve();
            }
        }, function(jqXHR, textStatus, errorThrown){    //任何形式的验证失败，都触发重新上传
            task.resolve();
        });

        return $.when(task);
    }
    , afterSendFile: function(file){
        var chunksTotal = 0;
        if((chunksTotal = Math.ceil(file.size/chunkSize)) > 1){
            //合并请求
            var task = new $.Deferred();
            $.ajax({
                type: "POST"
                , url: checkUrl
                , data: {
                    status: "chunksMerge"
                    , name: uniqueFileName
                    , chunks: chunksTotal
                    , ext: file.ext
                    , md5: md5Mark
                }
                , cache: false
                , dataType: "json"
            }).then(function(data, textStatus, jqXHR){

                //todo 检查响应是否正常

                task.resolve();
                file.path = data.path;
                UploadComlate(file);

            }, function(jqXHR, textStatus, errorThrown){
                task.reject();
            });

            return $.when(task);
        }else{
            UploadComlate(file);
        }
    }
});

var uploader = WebUploader.create({
	swf: "Uploader.swf"
	, server: backEndUrl
	, pick: "#picker"
	, resize: false
	, dnd: "#theList"
	, paste: document.body
	, disableGlobalDnd: true
	, thumb: {
		width: 100
		, height: 100
		, quality: 70
		, allowMagnify: true
		, crop: true
		//, type: "image/jpeg"
	}
//	//压缩
//	, compress: {
//		quality: 90
//		, allowMagnify: false
//		, crop: false
//		, preserveHeaders: true
//		, noCompressIfLarger: true
//		,compressSize: 100000
//	}
    , compress: false
	, prepareNextFile: true
	, chunked: true
	, chunkSize: chunkSize
	, threads: true
	, formData: function(){return $.extend(true, {}, userInfo);}
	, fileNumLimit: 1
	, fileSingleSizeLimit: 100 * 1024 * 1024 * 1024
	, duplicate: true
});

//当有文件被添加进上传队列
uploader.on("fileQueued", function(file){
	
	$("#theList").append('<li id="'+file.id+'">' +
		'<img /><span>'+file.name+'</span><span class="itemUpload">上传</span><span class="itemStop">暂停</span><span class="itemDel">删除</span>' +
		'<div class="percentage"></div>' +
	'</li>');
	
	var $img = $("#" + file.id).find("img");
	
	//文件预览
	uploader.makeThumb(file, function(error, src){
		if(error){
			$img.replaceWith("<span>不能预览</span>");
		}

		$img.attr("src", src);
	});
	
});

$("#theList").on("click", ".itemUpload", function(){
	uploader.upload();

    //"上传"-->"暂停"
    $(this).hide();
    $(".itemStop").show();
});

$("#theList").on("click", ".itemStop", function(){
    uploader.stop(true);

    //"暂停"-->"上传"
    $(this).hide();
    $(".itemUpload").show();
});

//todo 如果要删除的文件正在上传（包括暂停），则需要发送给后端一个请求用来清除服务器端的缓存文件
$("#theList").on("click", ".itemDel", function(){
	uploader.removeFile($(this).parent().attr("id"));	//从上传文件列表中删除

	$(this).parent().remove();	//从上传列表dom中删除
});

//文件上传进度
uploader.on("uploadProgress", function(file, percentage){
	$("#" + file.id + " .percentage").text(percentage * 100 + "%");
});

function UploadComlate(file){
    console.log(file);
    $("#video").val("/"+file.path);
    $("#" + file.id + " .percentage").text("上传完毕");
    $(".itemStop").hide();
    $(".itemUpload").hide();
    $(".itemDel").hide();
}