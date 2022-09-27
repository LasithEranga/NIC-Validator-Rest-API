let requestBody = {
  requestId: "454564",
  requestDate: new Date().toLocaleString(),
  action: "updateUser",
  actionPerformedBy: "system",
  user: {},
};

let allUserCount = 0;
let allUsers = [];

const setUserCount = (element) => {
  requestBody.requestId = (Math.random() * 10000).toFixed(0);
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
        allUsers = data.users;
        allUserCount = data.users.length;
        element.innerHTML = `${allUserCount}  ${allUserCount > 1 ? "Users" : "User"}`;
        setGenderGraph();
        setNationaltyGraph();
        setAgeGroupGraph();
        genderChart.update();
        nationaltyChart.update();
        myChart.update();
        //set activity count
        setActivityCount(updatedCount, updatePercentage, 1, "update");
        setActivityCount(deletedCount, deletePercentage, 1, "delete");
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setFilteredCount = (element, interval) => {
  requestBody.requestId = (Math.random() * 10000).toFixed(0);
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
        element.innerHTML = `${data.users}  ${data.users > 1 ? "Users" : "User"}`;
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setActivityCount = (element, percentageElement, interval, filterBy) => {
  requestBody.requestId = (Math.random() * 10000).toFixed(0);
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
        percentageElement.innerHTML = ` + ${((data.users / allUserCount) * 100).toFixed(2)} %`;
        element.innerHTML = `${data.users < 10 ? "0" + data.users : data.users} `;
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setGenderGraph = () => {
  requestBody.requestId = (Math.random() * 10000).toFixed(0);
  requestBody.requestDate = new Date().toLocaleString();
  requestBody.action = "getGenderCount";
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
        loadingAnimationGenderChart.style.display = "none";
        genderChart.config.data.labels = data.xAxis;
        genderChart.config.data.datasets[0].data = data.yAxis;
        genderChart.update();
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setNationaltyGraph = () => {
  requestBody.requestId = (Math.random() * 10000).toFixed(0);
  requestBody.requestDate = new Date().toLocaleString();
  requestBody.action = "getNationaltyCount";
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
        loadingAnimationPieChart.style.display = "none";
        nationaltyChart.config.data.labels = data.xAxis;
        nationaltyChart.config.data.datasets[0].data = data.yAxis;
        nationaltyChart.update();
      }
    })
    .catch((error) => {
      console.log(error);
    });

};

const setAgeGroupGraph = () => {
  requestBody.requestId = (Math.random() * 10000).toFixed(0);
  requestBody.requestDate = new Date().toLocaleString();
  requestBody.action = "getAgeGroupGraph";
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
        loadingAnimationBarGraph.style.display="none";
        myChart.config.data.datasets[0].data = data.yAxis;
        myChart.config.data.labels = data.xAxis;
        myChart.update();
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

const setRecentActivities = (element) => {
  requestBody.requestId = Math.random() * 10000;
  requestBody.requestDate = new Date().toLocaleString();
  requestBody.action = "getRecentActivities";
  requestBody.actionPerformedBy = "Lasith";

  let template = "";

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
        let activities = data.users;

        activities.map((user) => {
          let timeNowHours = new Date().toLocaleTimeString("en-US", { hour12: false }).slice(0, 2);
          let timeNowMiniutes = new Date().toLocaleTimeString("en-US", { hour12: false }).slice(3, 5);

          let timeInMinutes = Number(timeNowHours * 60) + Number(timeNowMiniutes);
          let modifiedBefore = user.modified_at ? timeInMinutes - (Number(user.modified_at.slice(0, 2) * 60) + Number(user.modified_at.slice(3, 5))) : "";
          let createdBefore = timeInMinutes - (Number(user.created_at.slice(0, 2) * 60) + Number(user.created_at.slice(3, 5)));

          console.log(modifiedBefore);
          template += `
                <div class="d-flex col-11 my-2">
                <div class="">
                    <div class="p-2 recent-activity-icon rounded-4 d-flex justify-content-center align-items-center">
                    <i class="fa-solid fa-address-card fs-4 p-1"></i>
                    </div>
                </div>
                <div class="ps-3">
                    <div class="amarnath">${user.modified_at ? "User details updated" : "User details created"}</div>
                    <div class="text-secondary fs-6">${user.modified_at ? modifiedBefore + " min ago" : createdBefore + " min ago"}</div>
                </div>
                </div>`;
        });

        element.innerHTML = template;
      }
    })
    .catch((error) => {
      console.log(error);
    });
};
