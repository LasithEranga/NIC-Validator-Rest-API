const TableRow = (user,index) => {
  return `
        <div class=" d-flex py-2 bg-table rounded-0 ps-3 amarnath">
        <div class="text-center col">
            ${index}
        </div>
        <div class="col-1 ">
            ${user.nic}
        </div>
        <div class="col-2 text-start ps-3 fixed-lines-1" title="${user.fullName}">
            ${user.fullName}
        </div>
        <div class="col-2 fixed-lines-1 text-start ps-3" title="${user.address}">
            ${user.address}
        </div>
        <div class="col-1 text-center ">
            ${user.dob}
        </div>
        <div class="col-1 text-center ">
            ${user.age} yrs
        </div>
        <div class="col-1 text-center">
            ${user.nationality}
        </div>
        <div class="col text-center">
            ${user.gender.substring(0,1)}
        </div>
        <div class="col-2 text-nowrap text-center">
            ${user.modified_at ? user.modified_on + " " + user.modified_at : user.created_on + " " + user.created_at}
        </div>
        <div class="col d-flex">
        <span role="button" class="text-success" onclick="updateUserClickHandler(${user.id})" > edit </span>
        <span role="button" class="px-1" > / </span>
        <span role="button" class="text-danger" onClick="openDeleteWarning('${user.id}')"> delete </span>
        
        </div>

    </div>

        `;
};
