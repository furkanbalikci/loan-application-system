const customerTableBody = document.querySelector("#customerTableBody");
const createCustomerForm = document.querySelector("#createCustomerForm");

// Get all customers.
fetch("/api/v1/allCustomer")
    .then(response => response.json())
    .then(customers => {
        customers.reverse();
        customers.forEach(customer => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
        <td>${customer.id}</td>
        <td>${customer.tckn}</td>
        <td>${customer.firstName}</td>
        <td>${customer.lastName}</td>
        <td>${customer.phoneNum}</td>
        <td>${customer.incomeMonthly}</td>
        <td>${customer.dateOfBirth}</td>
        <td>${customer.deposit}</td>
        
        
        <td>
          <button onclick="editCustomer('${customer.id}')">Edit</button>
          <button onclick="deleteCustomer('${customer.id}')">Delete</button>
        </td>
      `;
            customerTableBody.appendChild(tr);
        });
    })
    .catch(error => console.error(error));

// Create a new customer.
createCustomerForm.addEventListener("submit", event => {
    event.preventDefault();
    const formData = new FormData(createCustomerForm);
    const newCustomer = {
        tckn: formData.get("tckn"),
        firstName: formData.get("firstName"),
        lastName: formData.get("lastName"),
        phoneNum: formData.get("phoneNum"),
        incomeMonthly: formData.get("incomeMonthly"),
        dateOfBirth: formData.get("dateOfBirth"),
        deposit:formData.get("deposit")
    };

    newCustomer.dateOfBirth = newCustomer.dateOfBirth.replace(/^(\d{4})-(\d{2})-(\d{2})$/, "$2/$3/$1");
    const newBirthDate = new Date(newCustomer.dateOfBirth)
    const curentDate = new Date();
    const baseLimitDate = new Date('1900-01-01')
    const diffTime = Math.abs(curentDate - newBirthDate);
    const diffYears = diffTime / (1000 * 60 * 60 * 24 * 365.25);

    if (newBirthDate > curentDate || newBirthDate < baseLimitDate){
        window.alert("Date format is not correct.")
    }else if (diffYears < 18){
        window.alert("You must be over 18 to apply.")
    }else{
        fetch("/api/v1/createCustomer", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newCustomer)
        })
            .then(response => response.json())
            .then(customer => {
                const tr = document.createElement("tr");
                tr.innerHTML = `
        <td>${customer.id}</td>
        <td>${customer.tckn}</td>
        <td>${customer.firstName}</td>
        <td>${customer.lastName}</td>
        <td>${customer.phoneNum}</td>
        <td>${customer.incomeMonthly}</td>
        <td>${customer.dateOfBirth}</td>
        <td>${customer.deposit}</td>
        
        <td>
          <button onclick="editCustomer('${customer.id}')">Edit</button>
          <button onclick="deleteCustomer('${customer.id}')">Delete</button>
        </td>
      `;
                customerTableBody.appendChild(tr);
                createCustomerForm.reset();
                window.location.reload()
            })
            .catch(error => console.error(error));
    }


});

// Edit a customer.
function editCustomer(customerId) {
    fetch(`/api/v1/getCustomerById/${customerId}`)
        .then(response => response.json())
        .then(customer => {
            const { tckn, firstName, lastName, phoneNum, incomeMonthly, dateOfBirth, deposit } = customer;

            const tcknInput = prompt("Enter Tc No:", tckn);
            const firstNameInput = prompt("Enter first name:", firstName);
            const lastNameInput = prompt("Enter last name:", lastName);
            const phoneInput = prompt("Enter phone:", phoneNum);
            const incomeMonthlyInput = prompt("Enter monthly income:", incomeMonthly);
            const dateOfBirthInput = prompt("Enter date of birth (dd/mm/yyyy):", dateOfBirth);
            const depositInput = prompt("Enter deposit:", deposit);

            const regex = /^\d{2}\/\d{2}\/\d{4}$/;

            if (regex.test(dateOfBirthInput)) {
                const updatedCustomer = {
                    tckn: tcknInput,
                    firstName: firstNameInput,
                    lastName: lastNameInput,
                    phoneNum: phoneInput,
                    incomeMonthly: incomeMonthlyInput,
                    dateOfBirth: dateOfBirthInput,
                    deposit: depositInput
                };

                fetch(`/api/v1/updateCustomer/${customerId}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(updatedCustomer)
                })
                    .then(response => response.json())
                    .then(customer => {
                        const tr = customerTableBody.querySelector(`tr:nth-child(${customer.id})`);
                        tr.innerHTML = `
            <td>${customer.id}</td>
            <td>${customer.tckn}</td>
            <td>${customer.firstName}</td>
            <td>${customer.lastName}</td>
            <td>${customer.phoneNum}</td>
            <td>${customer.incomeMonthly}</td>
            <td>${customer.dateOfBirth}</td>
            <td>${customer.deposit}</td>
            <td>
              <button onclick="editCustomer('${customer.id}')">Edit</button>
              <button onclick="deleteCustomer('${customer.id}')">Delete</button>
            </td>
          `;
                        window.location.reload()
                    })
                    .catch(error => console.error(error));
            } else {
                console.log("Invalid date format");
            }
        })
        .catch(error => console.error(error));
}


// Delete a customer.
function deleteCustomer(customerId) {
    if (confirm("Are you sure you want to delete this customer?")) {
        fetch(`/api/v1/deleteCustomer/${customerId}`, {
            method: "DELETE"
        })
            .then(() => {
                const tr = customerTableBody.querySelector(`tr:nth-child(${customerId})`);
                tr.remove();
            })
            .catch(error => console.error(error),window.location.reload());

    }
}
