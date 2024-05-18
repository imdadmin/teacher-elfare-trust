// Function to display a greeting message
function displayGreeting() {
    alert("Hello, welcome to our website!");
}

// Function to validate a form
function validateForm() {
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;

    if (name === "" || email === "") {
        alert("Please enter your name and email address.");
        return false;
    }

    return true;
}
