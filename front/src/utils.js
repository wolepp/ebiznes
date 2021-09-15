import Cookie from "js-cookie";

// export default async function sendRequest(url, method, { params = undefined, data = undefined } = {}) {
//
//   switch (method.toUpperCase()) {
//     case "GET":
//       if (params) {
//         return await ky.get(url, { searchParams: params, credentials: "include" }).json();
//       }
//       return await ky.get(url, { credentials: "include" }).json();
//     case "POST":
//       return await ky.post(url, { json: data, credentials: 'include' }).json();
//     case "PUT":
//       break;
//     case "DELETE":
//       break;
//     default:
//       return new Promise(() => undefined);
//   }
// }

export default async function sendRequest(url, data, method='GET') {
  const result = await fetch(url, {
    method,
    headers: {
      'Accept': 'application/json',
      'Content-type': 'application/json',
      'Csrf-Token': Cookie.get('csrfToken'),
    },
    mode: 'cors',
    credentials: 'include',
    body: data ? JSON.stringify(data) : undefined,
  })
  return result.json();
}

const zip = (a, b) => a.map((k, i) => [k, b[i]]);

export {
  zip
};
