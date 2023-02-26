document.querySelector("#user-registration").addEventListener("submit", (event) => {
    event.preventDefault()
    const formData = new FormData(event.target)
    const user = Object.fromEntries(formData)

    var username = document.getElementById("username").value
    var email = document.getElementById("email").value
    var password = document.getElementById("password").value

    fetch('/api/v1/register?username=' + username + '&email=' + email + '&password=' + password, {

        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(user),
    })
        .then(response => response.json())
        .then(data => {
            console.log(data)
            if (data.dbStatus === true){
                window.alert("Bu kullanıcı adı alınmış.")
            }else{
                window.alert("Kayıt Başarılı.")
                window.location.href = "/api/v1/login"
            }
        })
        .catch(error => console.error(error))
})
