//sets the pagination section content. 
/**
 * parameters
 * noOfPages(required) - number of pages needs to render all users
 * rowsPerPage(requried) - number of rows table contains in one page
 * offset(default = 0 ) - the value that function starts the pagination.
 * 
 */
const pagination = (noOfPages, rowsPerPage, offset = 0) => {
  let contentHtml = "";
  let btnArray = [];

  //create the array of elemets that contains the buttons
  for (let i = 0; i < noOfPages; i++) {
    btnArray.push(`<span  role="button" onclick="setTableRows(${i * rowsPerPage})" id="pg-${i}" class="mx-3 fw-bold notselected-page pg-btn text-primary  ">${i + 1}</span>`);
  }

  //  check no of pages
  if (noOfPages > 3) {
    if (offset + 1 === btnArray.length - 1) {
      contentHtml += btnArray[offset] + btnArray[offset + 1];
    } else {
      contentHtml += btnArray[offset] + btnArray[offset + 1] + "..." + btnArray[btnArray.length - 1];
    }
  } else {
    btnArray.map((btn) => {
      contentHtml += btn;
    });
  }

  return contentHtml;
};
