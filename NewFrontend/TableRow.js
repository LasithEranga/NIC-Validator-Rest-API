const TableRow = (
  nic,
  fullName,
  address,
  birthday,
  nationalty,
  gender,
  editBtn,
  deleteBtn
) => {
  return `
        <div class=" d-flex py-2 my-1 bg-table rounded-2 ps-3 amarnath">
        <div class="col-2 ">
            ${nic}
        </div>
        <div class="col-2 ">
            ${fullName}
        </div>
        <div class="col-2 ">
            ${address}
        </div>
        <div class="col-1  ">
            ${birthday}
        </div>
        <div class="col-2 ">
            ${nationalty}
        </div>
        <div class="col-1 ">
            ${birthday}
        </div>
        <div class="col ps-5">
            edit/delete
        </div>

    </div>

        `;
};
