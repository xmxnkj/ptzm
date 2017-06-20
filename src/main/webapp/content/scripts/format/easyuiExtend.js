$.extend($.fn.validatebox.defaults.rules, {    
    phoneNum: { //验证手机号   
        validator: function(value, param){ 
         return /^1[3-9]+\d{9}$/.test(value);
        },    
        message: '请输入正确的手机号码。'   
    },
    
    telNum:{ //既验证手机号，又验证座机号
      validator: function(value, param){ 
          return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3)|(\d{3}\-))?(1[358]\d{9})$)/.test(value);
         },    
         message: '请输入正确的电话号码。' 
    }  
});