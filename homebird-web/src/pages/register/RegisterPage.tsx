import {
    Box,
    Button,
    Container,
    FormControl,
    FormHelperText,
    FormLabel,
    Heading,
    HStack,
    Input, Stack,
    Text, useBreakpointValue, useColorModeValue
} from '@chakra-ui/react';
import { UserApi, UserRequest } from 'api/user-api';
import { Logo } from 'components/Logo';
import { ValidationError, ValidationErrors } from 'components/validation/ValidationErrors';
import { useState } from 'react';
import { Link } from 'react-router-dom';


/**
 * RegisterPage
 * 
 * @returns 
 */
export const RegisterPage = () => {

    // form object
    const userRequestDefault: UserRequest = {
        email: "",
        password: ""
    };

    const [userRequest, setUserRequest] = useState(userRequestDefault);
    const [errors, setErrors] = useState<Array<ValidationError>>([]);

    const formHandler = async (ev: React.ChangeEvent<HTMLFormElement>) => {
        ev.preventDefault();
        setUserRequest(userRequest);

        await UserApi.createUser(userRequest)
            .then((response) => {
                // handle success
                console.log('success');
                console.log(response);
                setUserRequest(userRequestDefault);
            })
            .catch((error) => {
                if (error.response) {
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2x
                    setErrors(error.response.data);
                    return;

                } else if (error.request) {
                    // The request was made but no response was received
                    // `error.request` is an instance of XMLHttpRequest in the browser and an instance of
                    // http.ClientRequest in node.js
                    console.log(error.request);
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }

            });



    };

    const handleInputChange = (ev: React.ChangeEvent<HTMLInputElement>) => {

        setUserRequest({
            ...userRequest,
            [ev.target.name]: ev.target.value
        })
    }

    return (
        <Container
            maxW="lg"
            py={{
                base: '12',
                md: '24',
            }}
            px={{
                base: '0',
                sm: '8',
            }}
        >


            <form onSubmit={formHandler}>
                <Stack spacing="8">
                    <Stack spacing="6" align="center">
                        <Logo />
                        <Stack spacing="3" textAlign="center">
                            <Heading size={useBreakpointValue({ base: 'xs', md: 'sm' })}>Create an account</Heading>
                            <Text color="muted">Every bird needs a home</Text>
                        </Stack>
                    </Stack>

                    <Box
                        py={{
                            base: '0',
                            sm: '8',
                        }}
                        px={{
                            base: '4',
                            sm: '10',
                        }}
                        bg={useBreakpointValue({
                            base: 'transparent',
                            sm: 'bg-surface',
                        })}
                        boxShadow={{
                            base: 'none',
                            sm: useColorModeValue('md', 'md-dark'),
                        }}
                        borderRadius={{
                            base: 'none',
                            sm: 'xl',
                        }}
                    >

                        <ValidationErrors errors={errors} />

                        <Stack spacing="6">
                            <Stack spacing="5">
                                <FormControl isRequired>
                                    <FormLabel htmlFor="email">Email</FormLabel>
                                    <Input id="email" name='email' type="email" value={userRequest.email} onChange={handleInputChange} />
                                </FormControl>
                                <FormControl isRequired>
                                    <FormLabel htmlFor="password">Password</FormLabel>
                                    <Input id="password" name='password' type="password" value={userRequest.password} onChange={handleInputChange} />
                                    <FormHelperText color="muted">Please use a password manager</FormHelperText>
                                </FormControl>
                            </Stack>
                            <Stack spacing="4">
                                <Button variant="primary" type="submit">Create account</Button>
                            </Stack>
                        </Stack>
                    </Box>
                    <HStack justify="center" spacing="1">
                        <Text fontSize="sm" color="muted">
                            Already have an account?
                        </Text>
                        <Link to="/login">
                            <Button variant="link" colorScheme="blue" size="sm">
                                Log in
                            </Button>
                        </Link>
                    </HStack>
                </Stack>

            </form>
        </Container>
    )
}