
let formLogin = document.getElementById("login-form");
let mess = document.getElementById("mess");
formLogin.onsubmit =(e)=>{
    e.preventDefault();

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    if(!email.trim() || !password.trim()){
        mess.innerText="Vui lòng nhập đủ thông tin";
        return;
    }
    mess.innerText = "";
    $.ajax({
        url: '/api/auth/login-handle',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( {
            email: email,
            password: password
        }),
        success: function(response) {
            console.log(response)
            location.href=response;
        },
        error: function(xhr, status, error) {
            alert(error);
            console.log(xhr,status,error);
        }
      });
}