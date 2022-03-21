import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { act } from 'react-dom/test-utils';
import AddItemForm from './AddItemForm';

function mockFetch(data){
    return jest.fn().mockImplementation(()=>
        Promise.resolve({
            ok:true,
            json: () => data
        })
    );
}

test('render addItemForm', () =>{
    render(<AddItemForm />);
    const nameElement = screen.getByText("New");
    expect(nameElement).toBeInTheDocument();
});

test('click Add button and show the hidden form', () => {

    const fakeNewItem = jest.fn(); 

    render(<AddItemForm onNewItem={fakeNewItem}/>);

    userEvent.click(screen.getByRole("button",{name:"New"}));

    const xelement = screen.getByText("Add New Task");
    expect(xelement).toBeInTheDocument();

    fetch = mockFetch(JSON.stringify({id:1,description:"task 1",completed:false,dueDate:Date.now()}));

    const inputEvaluate = screen.getByPlaceholderText("New Task");

    const submitElement = screen.getByRole("button",{name:"Add"});
    expect(submitElement).toBeDisabled();

    userEvent.type(inputEvaluate,"Task1");
    expect(inputEvaluate).toHaveValue("Task1");
    
    expect(submitElement).toBeInTheDocument();
    expect(submitElement).not.toBeDisabled();
   
    act(() =>{
        userEvent.click(submitElement);
    });
    
    expect(fetch).toHaveBeenCalledTimes(1);

});
