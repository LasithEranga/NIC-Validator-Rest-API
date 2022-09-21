const TableRow = (
  user
) => {
  return `
        <div class=" d-flex py-2 bg-table rounded-0 ps-3 amarnath">
        <div class="col-2 ">
            ${user.nic}
        </div>
        <div class="col-2 fixed-lines ">
            ${user.fullName}
        </div>
        <div class="col-2  fixed-lines">
            ${user.address}
        </div>
        <div class="col-1  ">
            ${user.dob}
        </div>
        <div class="col-2 ">
            ${user.nationality}
        </div>
        <div class="col-1 ">
            ${user.gender}
        </div>
        <div class="col ps-5 d-flex">
        <div><button class="text-success d-flex justify-content-center align-items-center btn rounded-0" style="width:35px;height: 35px;" onclick="updateUserClickHandler('${user.id}')"><i class="fa-sharp fa-solid fa-user-pen"></i></button></div>
        <div><button type="button" class="text-danger me-2 d-flex justify-content-center align-items-center btn rounded-0" style="width:35px;height: 35px;" onClick="openDeleteWarning('${user.id}')"><i class="fa-solid fa-user-minus"></i></button></div>
        
        </div>

    </div>

        `;
};
