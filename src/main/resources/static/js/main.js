// Listener
function Menu() {
  
  var sidebar = document.getElementById("sidebar");
  var mainContent = document.getElementById("main-content");
  var hSidebar = document.getElementById("h-sidebar");
  var hMain = document.getElementById("h-main");
  if (sidebar.style.display === "none") {
    sidebar.style.display = "block";
    hSidebar.style.display = "block";
    mainContent.style.width = "70%"; // Adjust this value as needed
    hMain.style.width = "70%"; // Adjust this value as needed
  } else {
    sidebar.style.display = "none";
    mainContent.style.width = "100%";
    hSidebar.style.display = "none";
    hMain.style.width = "100%";
  }

}

// Functions
function AccountDropDown() {
console.log("wy");
var accountDiv = document.getElementById("accountDiv");
if (accountDiv.classList.contains("hidden")) {
accountDiv.classList.remove("hidden");
console.log("d");
} else {
accountDiv.classList.add("hidden");
console.log("dr");
}
}

function SettingDropDown() {
var settingDiv = document.getElementById("settingDiv");
if (settingDiv.classList.contains("hidden")) {
settingDiv.classList.remove("hidden");
console.log("d");
} else {
settingDiv.classList.add("hidden");
console.log("dr");
}
}

function ReportDropDown() {
var reportDiv = document.getElementById("reportDiv");
if (reportDiv.classList.contains("hidden")) {
reportDiv.classList.remove("hidden");
console.log("d");
} else {
reportDiv.classList.add("hidden");
console.log("dr");
}
}

function ProfileDropDown() {
var profleDropSection = document.getElementById("profleDropSection");
if (profleDropSection.classList.contains("hidden")) {
profleDropSection.classList.remove("hidden");
console.log("d");
} else {
profleDropSection.classList.add("hidden");
console.log("dr");
}
}
