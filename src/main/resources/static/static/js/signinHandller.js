
let formLogin = document.getElementById("sign-up-form");
let mess = document.getElementById("mess");
formLogin.onsubmit =(e)=>{
    e.preventDefault();

    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    let roleId = document.getElementById("role").value;

    if(!email.trim() || !password.trim()){
        mess.innerText="Please fill in all information";
        return;
    }
    mess.innerText = "";
    $.ajax({
        url: '/api/auth/signin-handle',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify( {
            email: email,
            password: password,
            roleId: roleId,

        }),
        success: function(response) {
            console.log(response)
            location.href="/login";
        },
        error: function(xhr, status, error) {
            alert(error);
            console.log(xhr,status,error);
        }
    });
}