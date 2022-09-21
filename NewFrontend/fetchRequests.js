let requestBody = {
  requestId: "454564",
  requestDate: new Date().toLocaleString(),
  action: "updateUser",
  actionPerformedBy: "system",
  user: {},
};

const setUserCount = (element) => {
  requestBody.requestId = Math.random() * 10000;
  requestBody.requestDate = new Date().toLocaleString();
  requestBody.action = "allUsers";
  requestBody.actionPerformedBy = "Lasith";

  fetch("http://localhost:8080/RestAPI/NICValidator/user", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(requestBody),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.responseCode === 1) {
        //returns the count of users
        allUsers = data.users;
        allUserCount = data.users.length;
        element.innerHTML = `${allUserCount}  ${
          allUserCount > 1 ? "Users" : "User"
        }`;
        genderChart.config.data.datasets[0].data = setGenderGraph();
        nationaltyChart.config.data.datasets[0].data = setNationaltyGraph();
        myChart.config.data.datasets[0].data = setAgeGroupGraph();
        genderChart.update();
        nationaltyChart.update();
        myChart.update();
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setFilteredCount = (element, interval) => {
  requestBody.requestId = Math.random() * 10000;
  requestBody.requestDate = new Date().toLocaleString();
  requestBody.range = interval;
  requestBody.action = "filteredResult";
  requestBody.actionPerformedBy = "Lasith";

  fetch("http://localhost:8080/RestAPI/NICValidator/user", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(requestBody),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.responseCode === 1) {
        element.innerHTML = `${data.users}  ${
          data.users > 1 ? "Users" : "User"
        }`;
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setActivityCount = (element, percentageElement, interval, filterBy) => {
  requestBody.requestId = Math.random() * 10000;
  requestBody.requestDate = new Date().toLocaleString();
  requestBody.range = interval;
  requestBody.action = "getActivities";
  requestBody.actionPerformedBy = "Lasith";
  requestBody.filterByActivity = filterBy;

  fetch("http://localhost:8080/RestAPI/NICValidator/user", {
    method: "POST",
    headers: {
      "Content-type": "application/json",
    },
    body: JSON.stringify(requestBody),
  })
    .then((response) => response.json())
    .then((data) => {
      if (data.responseCode === 1) {
        percentageElement.innerHTML = ` + ${(
          (data.users / allUserCount) *
          100
        ).toFixed(2)} %`;
        element.innerHTML = `${
          data.users < 10 ? "0" + data.users : data.users
        } `;
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setGenderGraph = () => {
  let maleCount = 0;
  let femaleCount = 0;

  allUsers.map((user) => {
    if (user.gender === "Male") {
      maleCount++;
    } else if (user.gender === "Female") {
      femaleCount++;
    }
  });

  return [maleCount, femaleCount];
};

const setNationaltyGraph = () => {
  let Sinhalese = 0;
  let Hindu = 0;
  let Islamic = 0;
  console.log(allUsers);
  allUsers.map((user) => {
    if (user.nationality === "Sinhalese") {
      Sinhalese++;
    } else if (user.nationality === "Hindu") {
      Hindu++;
    } else if (user.nationality === "Islamic") {
      Islamic++;
    }
  });

  return [Sinhalese, Hindu, Islamic];
};

const setAgeGroupGraph = () => {
  let age18To30 = 0;
  let age30To40 = 0;
  let age40To50 = 0;
  let age50To60 = 0;
  let age60To70 = 0;
  let age70To80 = 0;

  //map the users and get their birthday
  //tap into their age by calculating the birthday

  allUsers.map((user) => {
    let currentYear = new Date().getFullYear();
    let age = currentYear - user.dob.getFullYear();

    if (age > 70 && age < 80) {
      age70To80++;
    } else if (age > 60) {
      age60To70++;
    } else if (age > 50) {
      age50To60++;
    } else if (age > 40) {
      age40To50++;
    } else if (age > 30) {
      age30To40++;
    } else {
      age18To30++;
    }
  });

  return [age18To30, age30To40, age40To50, age50To60, age60To70, age70To80];
};