import ky from "ky";

async function sendRequest(url, method, data) {
  switch (method) {
  case "GET":
    return await ky.get(url).json();
  case "POST":
    return await ky.post(url, {json: data}).json();
  case "PUT":
    break;
  case "DELETE":
    break;
  default:
      return new Promise(() => undefined);
  }
}

export default sendRequest;
