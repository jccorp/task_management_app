import React from "react";
import * as ReactBootstrap from 'react-bootstrap';

const { REACT_APP_API_ENDPOINT } = process.env;

export default function AddItemForm({ onNewItem }) {
    const { Button, Modal, Form ,Container, Col} = ReactBootstrap;
    
    const [newItem, setNewItem] = React.useState('');
    const [newDueDate, setNewDueDate] = React.useState(Date.now());
  
    const [show, setShow] = React.useState(false);
  
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
  
  
    const submitNewItem = e => {
      e.preventDefault();
      fetch(REACT_APP_API_ENDPOINT, {
          method: 'POST',
          body: JSON.stringify({ description: newItem,
                                 dueDate: newDueDate,
                                 completed: false
           }),
          headers: { 'Content-Type': 'application/json' },
      })
          .then(r => r.json())
          .then(item => {
              onNewItem(item);
              setNewItem('');
              setNewDueDate(Date.now());
              setShow(false);
          });
  };
  
    return (
          <Container>  
              <Col md={{offset:10,span: 2}} className="spaceDownButton">
                  <Button
                      type="submit"
                      variant="success"
                      onClick={handleShow}
                      className="fillWidth">
                      New
                  </Button>
              </Col>
                    
              <Modal
                  show={show}
                  onHide={handleClose}
                  backdrop="static"
                  keyboard={false}
                  >
                  <Form onSubmit={submitNewItem}>
                      <Modal.Header closeButton>
                          <Modal.Title>Add New Task</Modal.Title>
                      </Modal.Header>
                      <Modal.Body>
                          <Form.Group className="mb-3" controlId="formDescription">
                              <Form.Label>Description:</Form.Label>
                              <Form.Control
                                  value={newItem}
                                  onChange={e => setNewItem(e.target.value)}
                                  type="text"
                                  placeholder="New Task"
                                  aria-describedby="basic-addon1"
                              />
                          </Form.Group>
                          <Form.Group className="mb-3" controlId="formDueDate">
                              <Form.Label>Due date:</Form.Label>
                              <Form.Control type="date" 
                                     data-testid="dueDate"
                                  value={newDueDate}  
                                  onChange={e => setNewDueDate(e.target.value)}
                              />
                          </Form.Group>
                      </Modal.Body>
                      <Modal.Footer>
                          <Button variant="secondary" onClick={handleClose}>
                              Close
                          </Button>
                          <Button type="submit" 
                              disabled={!newItem.length}
                              variant="primary">
                              Add
                          </Button>
                      </Modal.Footer>
                  </Form>
              </Modal>
          </Container>           
    );
}