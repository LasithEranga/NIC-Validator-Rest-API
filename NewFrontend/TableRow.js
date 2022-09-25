const TableRow = (user,index) => {
  return `
        <div class=" d-flex py-2 bg-table rounded-0 ps-3 amarnath">
        <div class=" px-4">
            ${index}
        </div>
        <div class="col-1 ">
            ${user.nic}
        </div>
        <div class="col-2 pe-1 fixed-lines-1" title="${user.fullName}">
            ${user.fullName}
        </div>
        <div class="col-2 fixed-lines-1" title="${user.address}">
            ${user.address}
        </div>
        <div class="col-1 text-center ">
            ${user.dob}
        </div>
        <div class="col-1 text-center">
            ${user.nationality}
        </div>
        <div class="col-1 text-center">
            ${user.gender}
        </div>
        <div class="col-2 text-nowrap text-center">
            ${user.modified_at ? user.modified_on + " " + user.modified_at : user.created_on + " " + user.created_at}
        </div>
        <div class="col ps-4 d-flex">
        <span role="button" class="text-success" onclick="updateUserClickHandler(${user.id})" > edit </span>
        <span role="button" class="px-1" > / </span>
        <span role="button" class="text-danger" onClick="openDeleteWarning('${user.id}')"> delete </span>
        
        </div>

    </div>

        `;
};
