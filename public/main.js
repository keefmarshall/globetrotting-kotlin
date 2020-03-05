/**
 * ---------------------------------------
 * This demo was created using amCharts 4.
 *
 * For more information visit:
 * https://www.amcharts.com/
 *
 * Documentation is available at:
 * https://www.amcharts.com/docs/v4/
 * ---------------------------------------
 */

// Evil globals. Bad Keith.
let parlJson;
let choiceJson;

function showData(data) {
    const countryCode = data.id;

    const infodiv = document.getElementById("infodiv");
    infodiv.innerHTML = "<h2>" + data.name + "</h2>";

    const choices = choiceJson[countryCode];
    if (choices != null) {
        const bestChoice = choices[0].country;
        if (bestChoice.iso2Code === countryCode) {
            infodiv.innerHTML += "<p>Best choice: stay put.</p>";
        } else {
            infodiv.innerHTML += "<p>Best choice: move to " + bestChoice.name + ".</p>"
                + "<p>" + data.value + "% to " + choices[0].score.toPrecision(2) + "%</p>"
        }
    } else {
        infodiv.innerHTML += "No data available for this country";
    }
}

async function init() {
    const parlResponse = await fetch("parl_data.json");
    parlJson = await parlResponse.json();

    const choiceResponse = await fetch("best-choices.json");
    choiceJson = await choiceResponse.json();

    /* Create map instance */
    const chart = am4core.create("chartdiv", am4maps.MapChart);

    /* Set map definition */
    chart.geodata = am4geodata_worldLow;

    /* Set projection */
    // chart.projection = new am4maps.projections.Orthographic();
    // chart.panBehavior = "rotateLongLat";
    chart.projection = new am4maps.projections.Miller();

    /* Create map polygon series */
    const polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());

    /* Make map load polygon (like country names) data from GeoJSON */
    polygonSeries.useGeodata = true;

    /* Configure series */
    const polygonTemplate = polygonSeries.mapPolygons.template;
    polygonTemplate.tooltipText = "{name} ({value}%)";
    polygonTemplate.fill = am4core.color("#FFF0E0"); // #74B266
    polygonTemplate.events.on("hit", function(ev) {
        ev.target.series.chart.zoomToMapObject(ev.target);

        const data = ev.target.dataItem.dataContext;
        showData(data);
    });

    /* Create hover state and set alternative fill color */
    const hs = polygonTemplate.states.create("hover");
    hs.properties.fill = am4core.color("#AA8800"); //#367B25

    // remove Antarctica
    polygonSeries.exclude = ["AQ"];

    chart.zoomControl = new am4maps.ZoomControl();

    // Add heat rule
    polygonSeries.heatRules.push({
        "property": "fill",
        "target": polygonSeries.mapPolygons.template,
        "min": am4core.color("#ffffff"),
        "max": am4core.color("#AA8800")
    });

    // Add value data
    polygonSeries.data = Object.keys(parlJson).map(key => {
        return { "id": key, "value": parlJson[key].toPrecision(2) };
    });
}
