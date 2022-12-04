import {
    Box,
    Button,
    Container,
    FormControl,
    FormHelperText,
    FormLabel,
    Heading,
    HStack,
    Input,
    Stack,
    Text, useBreakpointValue, useColorModeValue
} from '@chakra-ui/react'
import { Link } from 'react-router-dom'
import { Logo } from 'components/Logo'

export const RegisterPage = () => (
    <Container maxW="md" py={{ base: '12', md: '24' }}>
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
                <Stack spacing="6">
                    <Stack spacing="5">
                        <FormControl isRequired>
                            <FormLabel htmlFor="email">Email</FormLabel>
                            <Input id="email" type="email" />
                        </FormControl>
                        <FormControl isRequired>
                            <FormLabel htmlFor="password">Password</FormLabel>
                            <Input id="password" type="password" />
                            <FormHelperText color="muted">At least 8 characters long</FormHelperText>
                        </FormControl>
                    </Stack>
                    <Stack spacing="4">
                        <Button variant="primary">Create account</Button>
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
    </Container>
)