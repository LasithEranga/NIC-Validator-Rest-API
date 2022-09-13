const nicField = document.getElementById("nic");
const nicError = document.getElementById("nicError");

const maleRadioBtn = document.getElementById("male");
const femaleRadioBtn = document.getElementById("female");
const ageField = document.getElementById("age");

const fullNameField = document.getElementById("fullName");
const fullNameError = document.getElementById("fullNameError");

const dobField = document.getElementById("dob");
const dobError = document.getElementById("dobError");

const addressField = document.getElementById("address");
const addressError = document.getElementById("addressError");

const nationalityField = document.getElementById("nationality");
const nationalityError = document.getElementById("nationalityError");

const buttonClick = document.getElementById("btnOkay");
const form = document.getElementById("form");

const genderError = document.getElementById("genderError");

const nic = nicField.value;
const months = [31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

const showErrors = (inputElement,errorElement,messages) => {
    inputElement.classList.add("is-invalid");
    errorElement.innerText = messages;
    errorElement.classList.remove("d-none");
}

const hideErrors = (inputElement,errorElement) => {
    inputElement.classList.add("is-valid");
    inputElement.classList.remove("is-invalid");
    errorElement.classList.add("d-none");
}

const validateNIC = () => {
  let validNic = false;
  let oldNIC = false;
  let nic = nicField.value;

  if (nic.length === 0) {

    showErrors(nicField,nicError,"Please enter NIC number")

  } else {
    if (nic.length === 12) {
      if(nic.match(/^\d{12}/)){
        validNic = true;
        oldNIC = false;
      }else{
        showErrors(nicField,nicError,"Invalid NIC format");
        validNic = false;
      }
      
    } else if (nic.length === 10) {
      if(nic.match(/^\d{9}[vxVX]{1}/)){
        validNic = true;
        oldNIC = true;
      }else{
        showErrors(nicField,nicError,"Invalid NIC format");
        validNic = false;
      }
      
    }

    let gender = "";
    let year;
    let month;
    let date;
    let monthDate;
    let yearDigits;

    if (validNic) {
      //set nic as correct
      hideErrors(nicField,nicError)

      if (oldNIC) {
        yearDigits = Number(nic.substring(0, 2));
        monthDate = Number(nic.substring(2, 5));
        year = 1900 + yearDigits;
      } else {
        yearDigits = Number(nic.substring(0, 4));
        monthDate = Number(nic.substring(4, 7));
        year = yearDigits;
      }

      if (monthDate > 500) {
        monthDate = monthDate - 500;
        gender = "Female";
      } else {
        gender = "Male";
      }

      let tempMd = monthDate;

      for (let i = 0; i < 12; i++) {
        if (tempMd <= months[i]) {
          month = i + 1;
          break;
        } else {
          tempMd = tempMd - months[i];
        }
      }

      // date
      for (let i = 0; i < month - 1; i++) {
        monthDate = monthDate - months[i];
      }
      date = monthDate;

      return [year, month, date, gender,validNic];
    } else {
      //invalid nic
      //set field validation fail
      showErrors(nicField,nicError,"Please enter a valid NIC number")
      return [year, month, date, gender,validNic];
    }
  }
};

const generateDobGenderAge = () => {
    const [year, month, date, gender,isNicvalid] = validateNIC();
    if(isNicvalid){

        //setting age
        let dob = new Date(`${year}/${month}/${date}`);
        let currentYear = new Date().getFullYear();
        let age = currentYear - dob.getFullYear();
        ageField.value = age + " yrs";

        //setting gender
        if(gender === "Male"){
            maleRadioBtn.checked = true;
        }
        else {
            femaleRadioBtn.checked = true;
        }

        //setting dob
        let numOfMonthDigits = month.toString().length;
        if(numOfMonthDigits === 2){
            dobField.value = `${year}-${month}-${date}`;

        }else{
            dobField.value = `${year}-0${month}-${date}`;
        }
    }
    else{
        //resetting fields to defaults
        ageField.value = "";
        maleRadioBtn.checked = false;
        femaleRadioBtn.checked = false;
        dobField.value = "";
    }
}



const validateName = (nameField,errorField) => {

   let name = nameField.value;

    if(name.length === 0){
        //show error
        showErrors(nameField,errorField,"Please enter your full name")
        return false;

    }
    else if(!name.match(/^[a-zA-Z\s]+$/)){
        //show error msg
        showErrors(nameField,errorField,"Please do not use special characters")
        return false;

    }
    else{
        //hide error messages
        hideErrors(nameField,errorField) 
        return true;
    }
    
}


const validateAddress = (addressField,errorField) => {

    let address = addressField.value.toString();
    console.log(addressField.value);

    if(address.length === 0){
        //show error
        showErrors(addressField,errorField,"Please enter your address")
        return false;


    }
    else if(!address.match(/^[a-zA-Z0-9/.,\s]+$/)){
        //show error msg
        showErrors(addressField,errorField,"Address seems to be having some invalid characters")
        return false;


    }
    else{
        //hide error messages
        hideErrors(addressField,errorField) 
        return true;

    }
}


buttonClick.addEventListener("click", () => {
    //validate name 
    let isNameValid = validateName(fullNameField,fullNameError);
    let isAddressValid = validateAddress(addressField,addressError);

    if(isNameValid && isAddressValid){
        //enable the elements before they were submitted otherwise saves as null ;-(
        dobField.removeAttribute("disabled")
        maleRadioBtn.removeAttribute("disabled")
        femaleRadioBtn.removeAttribute("disabled")
        form.submit();
    }
});
