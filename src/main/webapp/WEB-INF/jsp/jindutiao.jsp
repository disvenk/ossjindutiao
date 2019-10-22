<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>进度条</title>
  <script src="http://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
  <script src="/assets/js/vue1.0.17.min.js"></script>
  <style type="text/css">
    .container{
      width: 480px;
      margin-top: 10px;
    }
    .progressBar {
      display: inline-block;
      width: 81%;
      height: 22px;
      background-color: rgba(0,0,0,0.4);
      -webkit-border-radius: 3px;
      -moz-border-radius: 3px;
      border-radius: 3px;
      margin-right: 3%;
    }
    #progressFill {
      width: 0%;
      height: 22px;
      position: relative;
      background-color: #40A4C2;
      border-radius: 3px;
      -webkit-border-radius: 3px;
      -moz-border-radius: 3px;
      background-size: 3em 3em;
      background-image: linear-gradient(-45deg, transparent 0em, transparent 0.8em, #57D1F7 0.9em, #57D1F7 2.1em, transparent 2.1em, transparent 2.9em, #57D1F7 3.1em);
      -webkit-animation: warning-animation 750ms infinite linear;
      -moz-animation: warning-animation 750ms infinite linear;
      animation: warning-animation 750ms infinite linear;
    }
    #progressFill:before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      height: 100%;
      border-radius: 3px;
      -webkit-border-radius: 3px;
      -moz-border-radius: 3px;
      background-image: linear-gradient(to bottom, #40A4C2, rgbA(37, 117, 188, 0.8) 15%, transparent 60%, #40A4C2);
  }
  @-moz-keyframes warning-animation {
    0% {
        background-position: 0 0;
    }
    100% {
        background-position: 3em 0;
    }
  }
  @-webkit-keyframes warning-animation {
      0% {
          background-position: 0 0;
      }
      100% {
          background-position: 3em 0;
      }
  }
  @-ms-keyframes warning-animation {
      0% {
          background-position: 0 0;
      }
      100% {
          background-position: 3em 0;
      }
  }
  @-o-keyframes warning-animation {
      0% {
          background-position: 0 0;
      }
      100% {
          background-position: 3em 0;
      }
  }
  @keyframes warning-animation {
      0% {
          background-position: 0 0;
      }
      100% {
          background-position: 3em 0;
      }
  }
  .progressText, #percentage {
    display: inline-block;
    margin-top: -11px;
    vertical-align: middle;
  }
  </style>     
</head>
<body id="app">
  <div>
    <input type="hidden" name="file" v-model="file">
    <input type="file" @change="uploadFile">
  </div>
  
  <%--<button id = "begin">点击开始</button>--%>
  <div class="container">
    <span class = "progressBar">
      <div id="progressFill"></div>
    </span>
    <span class = "progressText"> 进度  </span>
    <span id = "percentage">{{count}}%</span>
  </div>  
</body>
<script>
  var vm = new Vue({
      el:"#app",
      data:function () {
          return {
              types:[".zip"],
              postUrl:"/upload/file",
              percentNum:0,
              timer:null,
              count:0
          }
      },
      created: function () {
      
      },
      methods:{
          uploadFile:function(e){
              var that =this;
              var file = e.target.files[0];
              var filename = file.name;
              console.log(filename);
              //this.count = 0;
              this.timer = setInterval(function(){
                  that.getPercent();
                  // var percentageValue = that.count + '%'
                  // $("#percentage").html(percentageValue);
              },500)

              if(this.imageNameVailed(filename)){
                  var formdata = new FormData();
                  formdata.append("file",file);
                  $.ajax({
                      type: 'POST',
                      url:that.postUrl,
                      data:formdata,
                      contentType:false,
                      processData:false,
                      success: function (res) {

                      },
                      error: function () {

                      }
                  })
              }else{

              }
          },
          getPercent:function () {
              var that = this;
              $.ajax({
                  type: 'POST',
                  url:'/upload/item/percent',
                  success: function (res) {
                    console.log(res);
                    that.count = res;
                    $("#progressFill").animate({
                        width: res+"%"
                    });
                    if(res >= 100){
                      that.count = res;
                      clearInterval(that.timer);
                      that.resetPercent();
                    }
                  },
                  error: function () {

                  }
              })
          },
          resetPercent:function () {
              $.ajax({
                  type: 'POST',
                  url:'/upload/percent/reset',
                  success: function () {

                  }
              })
          },
          imageNameVailed:function(name){
              if(name.indexOf(".")!=-1){
                  // var lastName = name.substring(name.indexOf("."));
                  var split = name.split(".");
                  var lastName="."+split[split.length - 1];
                  for(var i in this.types){
                      var fix = this.types[i];
                      if(lastName==fix){
                          return true;
                      }
                  }
              }
              return false;
          }
      }
  })
</script>
</html>