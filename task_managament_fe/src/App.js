import React from "react";
import * as ReactBootstrap from 'react-bootstrap';

const { REACT_APP_API_ENDPOINT } = process.env; //"http://localhost:8080/tasks";

export default function BasicExample() {
  const { Container, Row, Col } = ReactBootstrap;

  return (
     <Container>
       <Row>
       <Col md={{ offset: 3, span: 6 }}>
         <Container>
           <Row>
           <Col md={{span: 2}}>
           <h1>Tasker</h1>
           </Col>
           <Col md={{offset:8,span: 2}}>
           Techformist
           </Col>
           </Row>
         </Container>
          
        </Col>
       </Row>
     <Row>
         <Col md={{ offset: 3, span: 6 }}>
             
             <TaskListCard />
         </Col>
     </Row>
 </Container>
  );
}


function TaskListCard() {
  const [items, setItems] = React.useState(null);

  React.useEffect(() => {
      fetch(REACT_APP_API_ENDPOINT)
          .then(r => r.json())
          .then(setItems);
  }, []);

  const onNewItem = React.useCallback(
      newItem => {
          setItems([...items, newItem]);
      },
      [items],
  );

  const onItemUpdate = React.useCallback(
      item => {
          const index = items.findIndex(i => i.id === item.id);
          setItems([
              ...items.slice(0, index),
              item,
              ...items.slice(index + 1),
          ]);
      },
      [items],
  );

  const onItemRemoval = React.useCallback(
      item => {
          const index = items.findIndex(i => i.id === item.id);
          setItems([...items.slice(0, index), ...items.slice(index + 1)]);
      },
      [items],
  );

  if (items === null) return 'Loading...';

  return (
      <React.Fragment>
         <AddItemForm onNewItem={onNewItem} /> 

          {items.length === 0 && (
              <p className="text-center">No tasks yet! Add one above!</p>
          )}
          {items.map(item => (
              <ItemDisplay
                  item={item}
                  key={item.id}
                  onItemUpdate={onItemUpdate}
                  onItemRemoval={onItemRemoval}
              />
          ))}
      </React.Fragment>
  );
}

function AddItemForm({ onNewItem }) {
  const { Button, Modal, Form ,Container, Col} = ReactBootstrap;
  
  const [newItem, setNewItem] = React.useState('');
  const [newDueDate, setNewDueDate] = React.useState(new Date());
  const [submitting, setSubmitting] = React.useState(false);

  const [show, setShow] = React.useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);


  const submitNewItem = e => {
    e.preventDefault();
    setSubmitting(true);
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
            setSubmitting(false);
            setNewItem('');
            setNewDueDate('');
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
                      className="fillWidth"
                      
                  >
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
                      placeholder="New Item"
                      aria-describedby="basic-addon1"
                  />
                  </Form.Group>
                  <Form.Group className="mb-3" controlId="formDueDate">
                        <Form.Label>Due date:</Form.Label>
                        <Form.Control type="date" 
                          
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
                    variant="primary">Add</Button>
                  </Modal.Footer>
                  </Form>
                </Modal>
              </Container>
              
  );
}


function ItemDisplay({ item, onItemUpdate, onItemRemoval }) {
  const { Container, Row, Col, Button } = ReactBootstrap;

  const toggleCompletion = () => {
      fetch(REACT_APP_API_ENDPOINT+`/${item.id}`, {
          method: 'PUT',
          body: JSON.stringify({
              description: item.description,
              completed: !item.completed,
              dueDate: item.dueDate,
          }),
          headers: { 'Content-Type': 'application/json' },
      })
          .then(r => r.json())
          .then(onItemUpdate);
  };

  const removeItem = () => {
      fetch(REACT_APP_API_ENDPOINT+`/${item.id}`, { method: 'DELETE' }).then(() =>
          onItemRemoval(item),
      );
  };

  return (
      <Container fluid className={`item ${item.completed && 'completed'}`}>
          <Row>             
              <Col xs={10} className="name">
                  <span className="boldDescription">{item.description}</span> <br/>
                  <i className="fa fa-calendar"></i>&nbsp;
                  {
                  new Intl.DateTimeFormat('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(item.dueDate)
                  }
              </Col>
              
              <Col xs={1} className="text-center">
                  <Button
                      className="toggles"
                      size="sm"
                      variant="link"
                      onClick={toggleCompletion}
                      aria-label={
                          item.completed
                              ? 'Mark task as incomplete'
                              : 'Mark task as complete'
                      }>
                      <i
                          className={`fa ${
                              item.completed ? 'fa-check-square' : 'fa-square'
                          }`}
                      />
                  </Button>
              </Col>
              <Col xs={1} className="text-center remove">
                  <Button
                      size="sm"
                      variant="link"
                      onClick={removeItem}
                      aria-label="Remove Task">
                      <i className="fa fa-trash text-danger" />
                  </Button>
              </Col>
          </Row>
      </Container>
  );
}
