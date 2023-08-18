import http from "k6/http";
import {check, sleep} from "k6";
import {htmlReport} from "https://raw.githubusercontent.com/benc-uk/k6-reporter/2.4.0/dist/bundle.js";
import exec from "k6/execution";

export const options = {
    scenarios: {
        load_test_scenario: {
            executor: "shared-iterations",
            vus: __ENV.VUS,
            iterations: __ENV.ITERATIONS,
        },
    },
};

export default function () {
    const tenantsCount = __ENV.TENANT_COUNT;

    const params = {
        headers: {
            "X-Tenant": `${(exec.scenario.iterationInTest % tenantsCount) + 1}`,
        },
        timeout: "5s",
    };

    const res = http.get('http://demo-app/notes/create', params);

    check(res, {
        "is 200": (r) => r.status >= 200 && r.status < 300,
        "is 400": (r) => r.status >= 400 && r.status < 500,
        "is 500": (r) => r.status >= 500 && r.status < 600,
    });
}

export function handleSummary(data) {
    return {
        [__ENV.HTML_SUMMARY_NAME]: htmlReport(data),
    };
}