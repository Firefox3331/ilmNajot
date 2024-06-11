import React, { useState } from 'react';
import { apiCall } from '../utils/apiCall'
import { useNavigate } from 'react-router-dom';
import './index.css';

function Register() {
    const [male, setMale] = useState(false);
    const [female, setFemale] = useState(false);
    const navigate = useNavigate();

    function submit(e) {
        e.preventDefault();
        let data = {
            id: null,
            firstName: e.target[0].value,
            lastName: e.target[1].value,
            username: e.target[2].value,
            email: e.target[2].value,
            phoneNumber: e.target[3].value,
            gender: male ? "MALE" : female ? "FEMALE" : "",
            schoolId: 1,
            password: e.target[6].value,
            rePassword: e.target[7].value,
        }
        apiCall("/auth/sign-up", 'post', data, null).then(res => {
            localStorage.setItem("token", res.data.token);
            if(localStorage.getItem("token") === res.data.token){
                navigate('/')
            }
        })
    }

    function chooseGender(e) {
        if (e.target.value === 'MALE') {
            setMale(true);
            setFemale(false);
        } else if (e.target.value === 'FEMALE') {
            setFemale(true);
            setMale(false);
        }
    }

    return (
        <div className='register'>
            <div className='container'>
                <div className="row wrapper">
                    <div className="card col-5 my-3">
                        <form onSubmit={submit} action="">
                            <div className="card-header">
                                <h5 className='text-center'>Register</h5>
                            </div>
                            <div className="card-body">
                                <input type="text" className='form-control my-2' placeholder='Firstname...' />
                                <input type="text" className='form-control my-2' placeholder='Lastname...' />
                                <input type="text" className='form-control my-2' placeholder='Email...' />
                                <input type="text" className='form-control my-2' placeholder='Phone Number...' />
                                <div className='d-flex row'>
                                    <div className='col-6'>
                                        <label className='d-block' htmlFor="male">Male</label>
                                        <input id='male' type="radio" onChange={(e) => chooseGender(e)} checked={male} value={"MALE"} />
                                    </div>
                                    <div className='col-6'>
                                        <label className='d-block' htmlFor="female">Female</label>
                                        <input id='female' onChange={(e) => chooseGender(e)} checked={female} type="radio" value={"FEMALE"} />
                                    </div>
                                </div>

                                <input type="text" className='form-control my-2' placeholder='Password...' />
                                <input type="text" className='form-control my-2' placeholder='Repeat Password...' />

                            </div>
                            <div className="card-footer">
                                <button className='btn btn-warning w-100'>submit</button>
                                <a href="http://localhost:3000/login">have you an account?</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Register;