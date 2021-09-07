import { useEffect, useState } from "react";
import { getOrderItems } from "../Services/OrderAPI";
import { Table } from "react-bootstrap";
import { zip } from "../utils"

const OrderItemsTable = ({ orderId }) => {

  const [zipOrderItemsProducts, setZipOrderItemsProducts] = useState([]);

  useEffect(() => {
    getOrderItems(orderId)
      .then(({ orderItems, products }) => {
        setZipOrderItemsProducts(zip(orderItems, products))
      });
  }, [orderId]);


  return (
    <Table striped bordered hover>
      <thead>
      <tr>
        <th>#</th>
        <th>Product name</th>
        <th>Quantity</th>
        <th>Price</th>
      </tr>
      </thead>
      <tbody>

      {zipOrderItemsProducts.map(([os, ps], i) => (
        <tr key={i}>
          <td>{i + 1}</td>
          <td>{ps.name}</td>
          <td>{os.quantity}</td>
          <td>{ps.price} PLN</td>
        </tr>
      ))}
      <tr>
        <td colSpan={3} className='text-end'>Total price</td>
        <td colSpan={1} className='fw-bold'>{
          zipOrderItemsProducts.reduce(
            (acc, cur) => acc + (cur[0].quantity * cur[1].price),
            0)
        } PLN
        </td>
      </tr>


      </tbody>

    </Table>
    // <Container>
    //   {orderItems.length > 0 && zip(orderItems, products).map(([os, ps]) => (
    //     <Row className='border-bottom'>
    //       <Col>
    //         {ps.name}
    //       </Col>
    //     </Row>
    //   ))}
    // </Container>
  );
};

export default OrderItemsTable;
