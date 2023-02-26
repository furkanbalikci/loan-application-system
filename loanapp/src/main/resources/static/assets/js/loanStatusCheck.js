const loanStatusCheckForm = document.querySelector("#loanStatusCheckForm");
const loanStatusCheckTableBody = document.querySelector("#loanStatusCheckTableBody");

// Listen to the form submission event
loanStatusCheckForm.addEventListener("submit", event => {
    event.preventDefault(); // Prevent form submission
    const formData = new FormData(loanStatusCheckForm);

    // Get the TCKN and date of birth values from the form
    var tckn = document.getElementById("tckn").value
    var dateOfBirth = document.getElementById("dateOfBirth").value
    dateOfBirth = dateOfBirth.replace(/^(\d{4})-(\d{2})-(\d{2})$/, "$3/$2/$1")
    const formBody = Object.fromEntries(formData)

    // Make a POST request to the loan status check API with the TCKN and date of birth values
    fetch("/api/v1/loanStatusCheck?tckn=" +tckn + "&dateOfBirth=" +dateOfBirth, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(formBody)
    })
        .then(response => response.json()) // Parse the response as JSON
        .then(data => {
            console.log(data) // Log the response data to the console

            // Get the application time as a Date object
            const date = new Date(data.applicationTime)

            // Determine the result string and class based on the result value
            let resultString = "";
            let resultClass = "";
            if (data.result ===1){
                resultString = "APPROVED";
                resultClass = "green";
            }else {
                resultString = "NOT APPROVED";
                resultClass = "red";
            }

            // Create a new table row with the loan status check data and append it to the table body
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${data.tckn}</td>
                <td>${data.firstName}</td>
                <td>${data.lastName}</td>
                <td>${data.incomeMonthly}</td>
                <td>${data.email}</td>
                <td>${data.phoneNum}</td>
                <td>${data.dateOfBirth}</td>
                <td>${data.deposit}</td>
                <td class="${resultClass}">${resultString}</td>
                <td>${data.limit}</td>
                <td>${date.toDateString()}</td>   
            `
            while (loanStatusCheckTableBody.firstChild) {
                loanStatusCheckTableBody.removeChild(loanStatusCheckTableBody.firstChild);
            }

            loanStatusCheckTableBody.appendChild(tr) // Append the new table row to the table body
        })
        .catch(error => console.error(error)); // Log any errors to the console
});
