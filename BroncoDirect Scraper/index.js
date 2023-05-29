const BRONCODIRECT_URL = "https://cmsweb.cms.cpp.edu";

function scrapeClasses() {
  if (window.location.origin != BRONCODIRECT_URL) return;

  const coursesTable = document.getElementById("ACE_$ICField$4$$0");
  if (!coursesTable) {
    alert("Failed to find courses. Please try again by reloading the page.");
  }
  /*
    scrape classes with schema of:
    {
      id - the course number displayed on broncodirect,
      course name,
      section number,
      room - formatted as building number - room number or TBA for no room,
      class instructor,
      days - formatted as each day the class meets with two letters, i.e. tuesday thursday as TuTh,
      start time - formatted as zero-padded 12-hour time string, ie. 01:00PM
      end time - formatted same as start time
      start date - formatted as YYYY-MM-DD date string
      end date - formatted same as start date
    }

  */
  let classes = [];
  const courses = [...coursesTable.rows];
  courses.forEach((course) => {
    // get course number for id and the course name
    const courseTitle = course.querySelector(".ui-collapsible-heading-toggle");
    let [courseTag, courseName] = courseTitle.innerText.split(" - ");

    const sections = course.querySelectorAll(".PSLEVEL1GRIDNBONBO");
    sections.forEach((section) => {
      let classSection = {};
      classSection["courseId"] = courseTag.split(" ")[1].trim();
      classSection["name"] = courseName.trim();
      const content = section.rows[1];
      const sectionFields = content.children;

      // get section number
      const sectionNumberElement =
        sectionFields[1].querySelector("a.PSHYPERLINK");
      const sectionNumberText = sectionNumberElement.innerText.split("-")[0];
      const sectionNumber = parseInt(sectionNumberText);
      classSection["section"] = sectionNumber;

      // get building and room number
      const roomElement = sectionFields[3].querySelector("span.PSLONGEDITBOX");
      const roomSplitString = roomElement.innerText.split(" ");
      if (roomSplitString.length == 4) {
        const building = roomSplitString[1].trim();
        const room = roomSplitString[3].trim();
        classSection["room"] = `${building}.${room}`;
      } else {
        classSection["room"] = "TBA";
      }

      // get instructor
      const instructorElement =
        sectionFields[4].querySelector("span.PSLONGEDITBOX");
      classSection["instructor"] =
        instructorElement.innerText.trim() == "To be Announced"
          ? "TBA"
          : instructorElement.innerText.trim();

      // get class meeting days and times
      const datesTimesElement =
        sectionFields[2].querySelector("span.PSLONGEDITBOX");
      const dateTimeStringSplit = datesTimesElement.innerText.split(" ");
      classSection["days"] =
        dateTimeStringSplit.length == 1 ? "TBA" : dateTimeStringSplit[0];
      classSection["startTime"] =
        dateTimeStringSplit.length == 1 ? "TBA" : dateTimeStringSplit[1];
      classSection["endTime"] =
        dateTimeStringSplit.length == 1 ? "TBA" : dateTimeStringSplit[3];
      classSection["startDate"] = "2023/21/01";
      classSection["endDate"] = "2023/12/05";

      classes.push(classSection);
    });
  });

  console.log(JSON.stringify(classes));
}

const mainContent = document.querySelector("#gh-main-content");

const observer = new MutationObserver((mutations) => {
  mutations.forEach((mutation) => {
    for (const node of mutation.addedNodes) {
      if (node.id === "win0divSSR_CLSRSLT_WRK_GROUPBOX1") {
        scrapeClasses();
      }
    }
  });
});

observer.observe(mainContent, {
  childList: true,
  subtree: true,
});
