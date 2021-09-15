import Cookie from "js-cookie";

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
