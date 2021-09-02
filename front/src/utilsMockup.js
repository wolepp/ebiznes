function sendRequest(url, method, data, mockData) {
  console.log(`Sending a ${method} request to ${url}`);

  switch (method) {
  case "GET":
    return mockData;
  case "POST":
    console.log("Data sent:")
    console.log(data);
    return mockData;
  case "PUT":
    break;
  case "DELETE":
    break;
  default:
    console.log(`SOMETHING WRONG, undefined method ${method}`)
  }
}

export default sendRequest;
