document.querySelector("#customer-form").addEventListener("submit", (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    const customer = Object.fromEntries(formData);
    customer.dateOfBirth = customer.dateOfBirth.replace(/^(\d{4})-(\d{2})-(\d{2})$/, "$3/$2/$1")

    var tckn = document.getElementById("tckn").value;
    var firstName = document.getElementById("firstName").value;
    var lastName = document.getElementById("lastName").value;
    var incomeMonthly = document.getElementById("incomeMonthly").value;
    var email = document.getElementById("email").value;
    var phoneNum = document.getElementById("phoneNum").value;
    var birthDate = document.getElementById("birth-date").value;
    var deposit = document.getElementById("deposit").value;


    const newBirthDate = new Date(birthDate)
    const curentDate = new Date();
    const baseLimitDate = new Date('1900-01-01')
    const diffTime = Math.abs(curentDate - newBirthDate);
    const diffYears = diffTime / (1000 * 60 * 60 * 24 * 365.25);

    if (newBirthDate > curentDate || newBirthDate < baseLimitDate){
        window.alert("Date format is not correct.")
    }else if (diffYears < 18){
        window.alert("You must be over 18 to apply.")
    } else{
        console.log("Json =  " + JSON.stringify(customer))
        fetch("/api/v1/loanapp", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(customer),
        })
            .then(response => response.json())
            .then(data => {
                console.log( data)
                var result ="";
                var limit = data.limit;



                if (data.result === 1){
                    result = "APPROVED"
                }else
                    result = "NOT APPROVED"


                if (data.dbStatus !== true){
                    window.alert("You can only apply for one loan.")
                }else{
                    //Shows Credit Result another page.
                    var newPage = window.open("creditResult.html", "", "width=600, height=600");
                    newPage.onload = function () {
                        newPage.document.querySelector("#result-value").innerHTML = result;
                        newPage.document.querySelector("#limit-value").innerHTML = limit + "TL";
                        newPage.document.querySelector("#tckn").innerHTML = tckn;
                        newPage.document.querySelector("#firstName").innerHTML = firstName;
                        newPage.document.querySelector("#lastName").innerHTML = lastName;
                        newPage.document.querySelector("#incomeMonthly").innerHTML = incomeMonthly + "TL";
                        newPage.document.querySelector("#email").innerHTML = email;
                        newPage.document.querySelector("#phoneNum").innerHTML = phoneNum;
                        newPage.document.querySelector("#birth-date").innerHTML = newBirthDate.toString();
                        newPage.document.querySelector("#deposit").innerHTML = deposit + "TL";

                    };

                }

            })
            .catch(error => console.error(error));
    }


});

document.addEventListener("DOMContentLoaded", function() {
    var tcknInput = document.getElementById("tckn");

    tcknInput.addEventListener("change", function() {
        var tckn = tcknInput.value;

        fetch("/api/v1/getCustomer/" + tckn)
            .then(function(response) {
                if (!response.ok) {
                    throw new Error("Customer not found");
                }
                return response.json();
            })
            .then(function(data) {
                console.log(data)

                const parts = data.dateOfBirth.split("/");
                const isoDate = `${parts[2]}-${parts[1].padStart(2, "0")}-${parts[0].padStart(2, "0")}`;

                console.log(isoDate); // "1952-10-01"
                document.getElementById("firstName").value = data.firstName;
                document.getElementById("lastName").value = data.lastName;
                document.getElementById("incomeMonthly").value = data.incomeMonthly;
                document.getElementById("email").value = data.email;
                document.getElementById("phoneNum").value = data.phoneNum;
                document.getElementById("deposit").value = data.deposit;
                document.getElementById("birth-date").value = isoDate;

            })
            .catch(function(error) {
                console.log(error)
            });
    });
});









