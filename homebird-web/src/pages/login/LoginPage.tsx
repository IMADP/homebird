import {
  Box,
  Button,
  Checkbox,
  Container,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Stack,
  Text,
  useBreakpointValue,
  useColorModeValue
} from '@chakra-ui/react';
import { Logo } from 'components/Logo';
import {
  Link,
  useLocation, useNavigate
} from "react-router-dom";
import { useAuthContext } from '../../auth/AuthContext';
import { PasswordField } from './PasswordField';

export const LoginPage = () => {
  let navigate = useNavigate();
  let location = useLocation();
  let auth = useAuthContext();

  let from = location.state?.from?.pathname || "/";

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);
    const email = formData.get("email") as string;
    const password = formData.get("password") as string;
    
    // redirect to their original destination
    auth.signin(email, password, () => { navigate(from, { replace: true }) });
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
      <Stack spacing="8">
        <Stack spacing="6">
          <Logo />
          <Stack
            spacing={{
              base: '2',
              md: '3',
            }}
            textAlign="center"
          >
            <Heading
              size={useBreakpointValue({
                base: 'xs',
                md: 'sm',
              })}
            >
              Log in to your account
            </Heading>
            <HStack spacing="1" justify="center">
              <Text color="muted">Every bird needs a home</Text>
            </HStack>
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
          <form onSubmit={handleSubmit}>
            <Stack spacing="6">
              <Stack spacing="5">
                <FormControl isRequired>
                  <FormLabel htmlFor="email">Email</FormLabel>
                  <Input id="email" name="email" type="email" required={false} />
                </FormControl>
                <PasswordField />
              </Stack>
              <HStack justify="space-between">
                <Checkbox defaultChecked>Remember me</Checkbox>
                <Button variant="link" colorScheme="blue" size="sm">
                  Forgot password?
                </Button>
              </HStack>
              <Stack spacing="6">
                <Button variant="primary" type='submit'>Sign in</Button>
              </Stack>
            </Stack>
          </form>
        </Box>
        <HStack justify="center" spacing="1">
          <Text fontSize="sm" color="muted">
            Don't have an account?
          </Text>
          <Link to="/register">
            <Button variant="link" colorScheme="blue" size="sm">
              Register
            </Button>
          </Link>
        </HStack>
      </Stack>
    </Container>)
}