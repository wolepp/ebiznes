import ky from "ky";

type Method = "GET" | "POST" | "PUT" | "DELETE"

async function sendRequest<T>(url: string, method: Method, data?: any): Promise<T> {
  switch (method) {
    case "GET":
      return await ky.get(url).json<T>();
    case "POST":
      return await ky.post(url, {json: data}).json<T>();
    case "PUT":
      break;
    case "DELETE":
      break;
  }
  return new Promise(() => undefined);
}

export default sendRequest;
