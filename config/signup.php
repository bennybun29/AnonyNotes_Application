<?php
// Database connection details
$servername = "localhost";
$username = "root"; // default username for XAMPP
$password = "";     // default password for XAMPP (usually empty)
$dbname = "anonynotes_db2"; // Replace with your database name

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get the POST data from the Android app
$email = $_POST['email'];
$username = $_POST['username'];
$password = $_POST['password'];

// Check if username or email already exists
$checkQuery = "SELECT * FROM users WHERE email = '$email' OR user_name = '$username'";
$result = $conn->query($checkQuery);

if ($result->num_rows > 0) {
    // User or email already exists
    echo json_encode(array("status" => "error", "message" => "Username or email already taken"));
    exit();
}

// Hash the password before saving it in the database
$hashed_password = password_hash($password, PASSWORD_DEFAULT);

// Set the current date for the created_at field
$created_at = date('Y-m-d');

// Insert the data into the database
$sql = "INSERT INTO users (user_name, email, password, created_at) VALUES ('$username', '$email', '$hashed_password', '$created_at')";

if ($conn->query($sql) === TRUE) {
    echo json_encode(array("status" => "success", "message" => "New record created successfully"));
} else {
    echo json_encode(array("status" => "error", "message" => $conn->error));
}

$conn->close();
?>
