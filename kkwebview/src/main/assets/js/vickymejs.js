var vickymejs = {};
vickymejs.os = {};
vickymejs.os.isIOS = /iOS|iPhone|iPad|iPod/i.test(navigator.userAgent);
vickymejs.os.isAndroid = !vickymejs.os.isIOS;
vickymejs.callbacks = {}

vickymejs.callback = function (callbackname, response) {
   var callbackobject = vickymejs.callbacks[callbackname];
   console.log("xxxx"+callbackname);
   if (callbackobject !== undefined){
       if(callbackobject.callback != undefined){
           console.log("xxxxxx"+response);
           var ret = callbackobject.callback(response);
           if(ret === false){
               return
           }
           delete vickymejs.callbacks[callbackname];
       }
   }
}

vickymejs.takeNativeAction = function(commandname, parameters){
    console.log("vickymejs takenativeaction")
    var request = {};
    request.name = commandname;
    request.param = parameters;
    if(window.vickymejs.os.isAndroid){
        console.log("android take native action" + JSON.stringify(request));
        window.vickymewebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.vickymewebview.postMessage(JSON.stringify(request))
    }
}

vickymejs.takeNativeActionWithCallback = function(commandname, parameters, callback) {
    var callbackname = "nativetojs_callback_" +  (new Date()).getTime() + "_" + Math.floor(Math.random() * 10000);
    vickymejs.callbacks[callbackname] = {callback:callback};

    var request = {};
    request.name = commandname;
    request.param = parameters;
    request.param.callbackname = callbackname;
    if(window.vickymejs.os.isAndroid){
        window.vickymewebview.takeNativeAction(JSON.stringify(request));
    } else {
        window.webkit.messageHandlers.vickymewebview.postMessage(JSON.stringify(request))
    }
}

window.vickymejs = vickymejs;
