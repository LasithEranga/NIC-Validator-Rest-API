const TableRow = (
  user
) => {
  return `
        <div class=" d-flex py-2 bg-table rounded-0 ps-3 amarnath">
        <div class="col-2 ">
            ${user.nic}
        </div>
        <div class="col-2 fixed-lines pe-1">
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
        <span role="button" class="text-success" onclick="updateUserClickHandler(${user.id})" > edit </span>
        <span role="button" class="px-1" > / </span>
        <span role="button" class="text-danger" onClick="openDeleteWarning('${user.id}')"> delete </span>
        
        </div>

    </div>

        `;
};
